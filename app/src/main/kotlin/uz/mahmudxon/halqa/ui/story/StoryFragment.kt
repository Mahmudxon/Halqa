package uz.mahmudxon.halqa.ui.story

import android.annotation.SuppressLint
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.SeekBar
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import uz.mahmudxon.halqa.R
import uz.mahmudxon.halqa.databinding.FragmentStoryBinding
import uz.mahmudxon.halqa.datasource.network.DownloadManger
import uz.mahmudxon.halqa.domain.model.AudioBook
import uz.mahmudxon.halqa.player.HalqaPlayer
import uz.mahmudxon.halqa.ui.base.BaseFragment
import uz.mahmudxon.halqa.util.FontManager
import uz.mahmudxon.halqa.util.Prefs
import uz.mahmudxon.halqa.util.theme.Theme
import uz.mahmudxon.halqa.util.toStringAsTime
import javax.inject.Inject

@AndroidEntryPoint
class StoryFragment : BaseFragment<FragmentStoryBinding>(R.layout.fragment_story),
    DownloadManger.OnDownloadListener, HalqaPlayer.PlayerListener, SeekBar.OnSeekBarChangeListener {

    private val viewModel by viewModels<StoryViewModel>()

    @Inject
    lateinit var fontManager: FontManager

    @Inject
    lateinit var prefs: Prefs

    private var chapterId: Int = 0

    lateinit var audioStatus: AudioBook.Status

    @SuppressLint("SetTextI18n")
    override fun onCreate(view: View) {
        chapterId = arguments?.getInt("id") ?: -1
        viewModel.getChapter(chapterId)
        HalqaPlayer.listener = this
        audioStatus = if (prefs.get(prefs.audioItemDownloaded + chapterId, false)) {
            if (HalqaPlayer.getPlayingId() == chapterId) AudioBook.Status.Playing(
                HalqaPlayer.position, HalqaPlayer.duration
            )
            else AudioBook.Status.Downloaded
        } else AudioBook.Status.Online(0L)
        binding.bottomPlayer.visibility = if (chapterId == HalqaPlayer.getPlayingId()) {
            setPlayerPosition(HalqaPlayer.position, HalqaPlayer.duration)
            View.VISIBLE
        } else View.GONE
        setIconsVisible()
        binding.seekBar.setOnSeekBarChangeListener(this)
        DownloadManger.setListener(this)
        binding.play.setOnClickListener {
            HalqaPlayer.playOrResume(chapterId)
            audioStatus = AudioBook.Status.Playing(HalqaPlayer.position, HalqaPlayer.duration)
            setIconsVisible()
        }
        binding.playing.setOnClickListener {
            HalqaPlayer.pause()
            audioStatus = AudioBook.Status.Downloaded
            setIconsVisible()
        }
        binding.backButton.setOnClickListener { finish() }
        binding.chapterLayout.fontSize = fontManager.fontSize
        binding.download.setOnClickListener {
            audioStatus = AudioBook.Status.Downloading(0, 1)
            setIconsVisible()
            DownloadManger.download(chapterId)
        }

        binding.downloading.setOnClickListener {
            DownloadManger.cancel(chapterId)
        }
        binding.iconOfBottomPlayer.setOnClickListener {
            audioStatus = if (audioStatus is AudioBook.Status.Playing) {
                HalqaPlayer.pause()
                AudioBook.Status.Downloaded
            } else {
                HalqaPlayer.playOrResume(chapterId)
                AudioBook.Status.Playing(HalqaPlayer.position, HalqaPlayer.duration)
            }
            setIconsVisible()
        }
        viewModel.chapter.observe(this) { state ->
            state.data?.let {
                binding.chapterLayout.chapter = it
                binding.title.text = it.title
                binding.titleOfPlayer.text = "${it.chapterNumber} - bob"
                binding.title.isSelected = true
            }
            binding.loading = state.loading
        }
    }

    override fun onCreateTheme(theme: Theme) {
        super.onCreateTheme(theme)
        binding.theme = theme
        binding.chapterLayout.theme = theme
    }

    override fun onProcess(id: Int, current: Long, total: Long) {
        if (chapterId == id) {
            if (audioStatus !is AudioBook.Status.Downloading) {
                audioStatus = AudioBook.Status.Downloading(current, total)
                setIconsVisible()
            }
            binding.downloadProgress.progress = ((current * 100) / total).toInt() + 2
        }
    }

    override fun onDownloadComplete(id: Int) {
        if (chapterId == id) {
            audioStatus = AudioBook.Status.Downloaded
            setIconsVisible()
            prefs.save(prefs.audioItemDownloaded + chapterId, true)
        }
    }

    override fun onDownloadCancelled(id: Int) {
        if (chapterId == id) {
            audioStatus = AudioBook.Status.Online(0L)
            setIconsVisible()
            binding.downloadProgress.progress = 2
        }
    }

    private fun setIconsVisible() {
        when (audioStatus) {
            is AudioBook.Status.Online -> {
                binding.download.visibility = View.VISIBLE
                binding.downloading.visibility = View.GONE
                binding.play.visibility = View.GONE
                binding.playing.visibility = View.GONE
            }
            is AudioBook.Status.Downloading -> {
                binding.download.visibility = View.GONE
                binding.downloading.visibility = View.VISIBLE
                binding.play.visibility = View.GONE
                binding.playing.visibility = View.GONE
                binding.downloadProgress.animation =
                    AnimationUtils.loadAnimation(context, R.anim.rotate)
                binding.downloadProgress.animate()
            }
            is AudioBook.Status.Playing -> {
                binding.download.visibility = View.GONE
                binding.downloading.visibility = View.GONE
                binding.playing.visibility = View.VISIBLE
                binding.play.visibility = View.GONE
                binding.bottomPlayer.visibility = View.VISIBLE
                binding.iconOfBottomPlayer.setImageResource(R.drawable.ic_pause)
            }
            is AudioBook.Status.Downloaded -> {
                binding.download.visibility = View.GONE
                binding.downloading.visibility = View.GONE
                binding.play.visibility = View.VISIBLE
                binding.playing.visibility = View.GONE
                binding.iconOfBottomPlayer.setImageResource(R.drawable.ic_play)
            }
        }
    }

    override fun onTrackEnded(id: Int) {
        if (chapterId == id) {
            audioStatus = AudioBook.Status.Downloaded
            setIconsVisible()
        }
    }

    override fun onPlaying(id: Int, position: Long, duration: Long) {
        if (id == chapterId) setPlayerPosition(position, duration)
    }

    @SuppressLint("SetTextI18n")
    private fun setPlayerPosition(position: Long, duration: Long) {
        binding.seekBar.progress = (position * 10000 / if (duration > 0) duration else 1).toInt()
        binding.descriptionOfPlayer.text =
            position.toStringAsTime() + " / " + duration.toStringAsTime()
    }

    @SuppressLint("SetTextI18n")
    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        if (fromUser) {
            binding.descriptionOfPlayer.text =
                (progress * HalqaPlayer.duration / 10000).toStringAsTime() + " / " + HalqaPlayer.duration.toStringAsTime()
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {

    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        HalqaPlayer.seek((seekBar?.progress ?: 1) * HalqaPlayer.duration / 10000)
    }
}