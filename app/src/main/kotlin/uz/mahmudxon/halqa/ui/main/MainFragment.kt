package uz.mahmudxon.halqa.ui.main

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.setPadding
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint
import uz.mahmudxon.halqa.R
import uz.mahmudxon.halqa.databinding.FragmentMainBinding
import uz.mahmudxon.halqa.databinding.FragmentSettingsBinding
import uz.mahmudxon.halqa.datasource.network.DownloadManger
import uz.mahmudxon.halqa.domain.model.AudioBook
import uz.mahmudxon.halqa.domain.model.Chapter
import uz.mahmudxon.halqa.player.HalqaPlayer
import uz.mahmudxon.halqa.ui.base.BaseFragment
import uz.mahmudxon.halqa.ui.base.list.SingleTypeAdapter
import uz.mahmudxon.halqa.ui.list.AudioBookAdapter
import uz.mahmudxon.halqa.ui.list.ChaptersAdapter
import uz.mahmudxon.halqa.ui.list.ThemeAdapter
import uz.mahmudxon.halqa.util.FontManager
import uz.mahmudxon.halqa.util.Prefs
import uz.mahmudxon.halqa.util.TAG
import uz.mahmudxon.halqa.util.dp
import uz.mahmudxon.halqa.util.logd
import uz.mahmudxon.halqa.util.theme.Theme
import javax.inject.Inject

@AndroidEntryPoint class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main),
    SingleTypeAdapter.OnItemClickListener<Chapter>, NavigationBarView.OnItemSelectedListener,
    View.OnClickListener, CompoundButton.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener,
    DownloadManger.OnDownloadListener, DownloadManger.AudioSizeListener,
    HalqaPlayer.PlayerListener {

    private val viewModel by viewModels<MainViewModel>()

    @Inject
    lateinit var chaptersAdapter: ChaptersAdapter

    @Inject
    lateinit var audioBookAdapter: AudioBookAdapter

    @Inject
    lateinit var themeAdapter: ThemeAdapter

    @Inject
    lateinit var fontManager: FontManager

    @Inject
    lateinit var prefs: Prefs

    private val audioBooks: ArrayList<AudioBook> by lazy {
        ArrayList<AudioBook>().also {
            for (x in 1..32) it.add(
                AudioBook(
                    id = x, title = "$x - боб", status = if (prefs.get(
                            prefs.audioItemDownloaded + x, false
                        )
                    ) {
                        if (HalqaPlayer.getPlayingId() == x) AudioBook.Status.Playing(
                            HalqaPlayer.position,
                            HalqaPlayer.duration
                        )
                        else AudioBook.Status.Downloaded
                    } else AudioBook.Status.Online(getAudioSize(x))
                )
            )
        }
    }

    lateinit var settingBinding: FragmentSettingsBinding

    override fun onCreate(view: View) {
        settingBinding = FragmentSettingsBinding.inflate(layoutInflater)
        settingBinding.onClick = this
        chaptersAdapter.setItemClickListener(this)
        DownloadManger.setListener(this)
        DownloadManger.setSizeListener(this)
        HalqaPlayer.listener = this
        binding.viewPager.adapter = ViewpagerAdapter()
        audioBookAdapter.listener = { onAudioClick(it) }
        binding.viewPager.offscreenPageLimit = 3
        binding.viewPager.isUserInputEnabled = false
        settingBinding.fontSizeSeekbar.setOnSeekBarChangeListener(this)
        settingBinding.fontSizeSeekbar.progress = fontManager.fontSize - 12
        settingBinding.autoThemeChange.isChecked = themeManager.autoDarkMode
        settingBinding.autoThemeChange.setOnCheckedChangeListener(this)
        binding.bottomNavigation.setOnItemSelectedListener(this)
        addAudioBooks()
        viewModel.getChaptersList()
        viewModel.chaptersState.observe(this) { state ->
            state.data?.let {
                chaptersAdapter.swapData(it)
                audioBookAdapter.swapData(audioBooks)
            }
        }
    }

    override fun onListItemClick(item: Chapter) {
        navController.navigate(
            R.id.action_mainFragment_to_storyFragment, bundleOf("id" to item.chapterNumber)
        )
    }


    private fun addAudioBooks() {
        if (!prefs.get(prefs.iKnowAudioSizes, false)) {
            // Download Audio Sizes
            DownloadManger.requestSizes()
        }

    }

    override fun onCreateTheme(theme: Theme) {
        super.onCreateTheme(theme)
        binding.theme = theme
        settingBinding.theme = theme
        val iconColorStates = ColorStateList(
            arrayOf(
                intArrayOf(-android.R.attr.state_checked), intArrayOf(android.R.attr.state_checked)
            ), intArrayOf(
                ContextCompat.getColor(requireContext(), theme.secondaryTextColor),
                ContextCompat.getColor(requireContext(), R.color.cl_color_accent)
            )
        )
        binding.bottomNavigation.itemIconTintList = iconColorStates
        binding.bottomNavigation.itemTextColor = iconColorStates
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.chapter -> {
                binding.viewPager.currentItem = 0
                binding.title.text = context?.getText(R.string.app_name)
                true
            }
            R.id.audio -> {
                binding.viewPager.currentItem = 1
                binding.title.text = context?.getText(R.string.audio_book)
                true
            }
            R.id.settings -> {
                binding.viewPager.currentItem = 2
                binding.title.text = context?.getText(R.string.settings)
                true
            }
            else -> false
        }
    }

    private inner class ViewpagerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        private var home: Int = -1
        private var audio: Int = -1
        private var settings: Int = -1

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view: View = when (viewType) {
                home, audio -> {
                    val list = RecyclerView(parent.context)
                    list.layoutManager = LinearLayoutManager(parent.context)
                    list.adapter = if (viewType == home) chaptersAdapter else audioBookAdapter
                    list.setPadding(8.dp)
                    list
                }
                settings -> settingBinding.root
                else -> {
                    View(parent.context)
                }
            }
            view.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            )
            return object : RecyclerView.ViewHolder(view) {}
        }

        override fun getItemViewType(position: Int) = position

        override fun getItemCount(): Int {
            var count = 0
            home = count++
            audio = count++
            settings = count++
            return count
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.changeTheme -> {
                settingBinding.autoThemeChange.isChecked = false
                changeTheme(settingBinding.themeIcon)
            }
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        themeManager.autoDarkMode = isChecked
        if (isChecked && themeChanger?.isSystemDark() != themeManager.currentTheme.isDark) buttonView?.let {
            changeTheme(
                it
            )
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun changeTheme(view: View) {
        if (themeChanger?.canChangeTheme() != true) return
        themeChanger?.takeScreenshot()
        themeManager.currentTheme =
            if (themeManager.currentTheme.isDark) themeManager.lastLightTheme else themeManager.lastDarkTheme
        chaptersAdapter.notifyDataSetChanged()
        audioBookAdapter.notifyDataSetChanged()
        notifyThemeChanged()
        val coordinate = IntArray(2)
        view.getLocationInWindow(coordinate)
        val x = coordinate[0] + (view.width / 2)
        val y = coordinate[1] //+ (view.height/2)
        themeChanger?.startCircularAnimation(x, y, !themeManager.currentTheme.isDark)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
        fontManager.fontSize = p1 + 12
        settingBinding.fontSize = fontManager.fontSize
        chaptersAdapter.notifyDataSetChanged()
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {

    }

    override fun onStopTrackingTouch(p0: SeekBar?) {

    }


    private fun onAudioClick(id: Int) {
        val index = id - 1
        logd("${audioBooks[index].status}")
        when (val status = audioBooks[index].status) {
            is AudioBook.Status.Online -> {
                audioBooks[index].status = AudioBook.Status.Downloading(0, 1)
                audioBookAdapter.notifyItemChanged(index)
                DownloadManger.download(id)
            }
            is AudioBook.Status.Downloading -> {
                DownloadManger.cancel(id)
            }
            is AudioBook.Status.Playing -> {
                HalqaPlayer.pause()

                audioBooks[id - 1].status =
                    AudioBook.Status.Downloaded
                audioBookAdapter.notifyItemChanged(index)
            }
            is AudioBook.Status.Downloaded -> {
                HalqaPlayer.playOrResume(id)
                audioBooks[id - 1].status =
                    AudioBook.Status.Playing(HalqaPlayer.position, HalqaPlayer.duration)
                audioBookAdapter.notifyItemChanged(index)
            }
        }
    }

    override fun onProcess(id: Int, current: Long, total: Long) {
        val index = id - 1
        if (audioBooks[index].status is AudioBook.Status.Downloading) {
            audioBooks[index].status = AudioBook.Status.Downloading(current, total)
            audioBookAdapter.notifyItemChanged(index, AudioBook.Status.Downloading(current, total))
        } else {
            audioBooks[index].status = AudioBook.Status.Downloading(current, total)
            audioBookAdapter.notifyItemChanged(index)
        }
    }

    override fun onDownloadComplete(id: Int) {
        val index = id - 1
        audioBooks[index].status = AudioBook.Status.Downloaded
        audioBookAdapter.notifyItemChanged(index)
        prefs.save(prefs.audioItemDownloaded + id, true)
    }

    override fun onDownloadCancelled(id: Int) {
        val index = id - 1
        audioBooks[index].status = AudioBook.Status.Online(getAudioSize(id))
        audioBookAdapter.notifyItemChanged(index)
    }

    override fun onSizesRevive(sizes: Map<String, Long>) {
        for ((key, value) in sizes) {
            prefs.save(prefs.downloadSize + key, value)
        }
        prefs.save(prefs.iKnowAudioSizes, true)
        addAudioBooks()
    }

    private fun getAudioSize(id: Int): Long =
        prefs.get(key = prefs.downloadSize + id, defValue = 0L)

    override fun onTrackEnded(id: Int) {
        val index = id - 1
        audioBooks[index].status = AudioBook.Status.Downloaded
        audioBookAdapter.notifyItemChanged(index, AudioBook.Status.Downloaded)
    }

    override fun onPlaying(id : Int, position: Long, duration: Long) {
        val index = id - 1
        val status = AudioBook.Status.Playing(position, duration)
        audioBooks[index].status = status
        audioBookAdapter.notifyItemChanged(index, status)
    }
}