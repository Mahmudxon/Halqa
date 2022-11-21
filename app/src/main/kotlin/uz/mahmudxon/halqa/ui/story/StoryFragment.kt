package uz.mahmudxon.halqa.ui.story

import android.view.View
import android.view.animation.AnimationUtils
import android.widget.SeekBar
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import uz.mahmudxon.halqa.R
import uz.mahmudxon.halqa.databinding.DialogAudioBinding
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
    DownloadManger.OnDownloadListener, HalqaPlayer.PlayerListener {

    private val viewModel by viewModels<StoryViewModel>()

    @Inject
    lateinit var fontManager: FontManager

    @Inject
    lateinit var prefs: Prefs

    var chapterId: Int = 0

    lateinit var audioStatus: AudioBook.Status

    private var dialog: BottomSheetDialog? = null

    private var dialogBinding: DialogAudioBinding? = null

    override fun onCreate(view: View) {
        chapterId = arguments?.getInt("id") ?: -1
        HalqaPlayer.listener = this
        DownloadManger.setListener(this)
        setData()

        binding.play.setOnClickListener {
            play()
        }
        binding.playing.setOnClickListener {
            showAudioDialog()
        }
        binding.backButton.setOnClickListener { finish() }
        binding.chapterLayout.fontSize = fontManager.fontSize
        binding.download.setOnClickListener {
            download()
        }

        binding.downloading.setOnClickListener {
            DownloadManger.cancel(chapterId)
        }
        viewModel.chapter.observe(this)
        { state ->
            state.data?.let {
                binding.chapterLayout.chapter = it
                binding.title.text = it.title
                binding.title.isSelected = true
                dialogBinding?.title = it.title
            }
            binding.loading = state.loading
        }
    }

    override fun onCreateTheme(theme: Theme) {
        super.onCreateTheme(theme)
        dialogBinding = DataBindingUtil.inflate(layoutInflater, R.layout.dialog_audio, null, false)
        binding.theme = theme
        dialogBinding?.theme = theme
        dialogBinding?.pause?.setOnClickListener {
            pause()
        }
        dialogBinding?.play?.setOnClickListener {
            play()
        }
        dialogBinding?.download?.setOnClickListener {
            download()
        }
        dialogBinding?.next?.setOnClickListener {
            next()
        }
        dialogBinding?.prev?.setOnClickListener {
            prev()
        }

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
                dialogBinding?.pause?.visibility = View.GONE
                dialogBinding?.play?.visibility = View.GONE
                dialogBinding?.download?.visibility = View.VISIBLE

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
                binding.playing.visibility =
                    View.VISIBLE
                binding.play.visibility = View.GONE
                dialogBinding?.pause?.visibility = View.VISIBLE
                dialogBinding?.play?.visibility = View.GONE
                dialogBinding?.download?.visibility = View.GONE

            }
            is AudioBook.Status.Downloaded -> {
                binding.download.visibility = View.GONE
                binding.downloading.visibility = View.GONE
                binding.play.visibility = View.VISIBLE
                binding.playing.visibility = View.GONE
                dialogBinding?.pause?.visibility = View.GONE
                dialogBinding?.play?.visibility = View.VISIBLE
                dialogBinding?.download?.visibility = View.GONE

            }
        }
        if (chapterId == 1)
            dialogBinding?.prev?.visibility = View.INVISIBLE
        else
            dialogBinding?.prev?.visibility = View.VISIBLE
        if (chapterId == 33)
            dialogBinding?.next?.visibility = View.INVISIBLE
        else
            dialogBinding?.next?.visibility = View.VISIBLE
    }

    override fun onTrackEnded(id: Int) {
        if (chapterId == id) {
            audioStatus = AudioBook.Status.Downloaded
            setIconsVisible()
        }
    }

    override fun onPlaying(id: Int, position: Long, duration: Long) {
        if (duration != 0L) {
            dialogBinding?.seekBar?.progress =
                (position * 10000 / duration).toInt()
        }
        dialogBinding?.seekBar?.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                HalqaPlayer.seek(seekBar.progress * duration / 10000)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onProgressChanged(
                seekBar: SeekBar,
                progress: Int,
                fromUser: Boolean
            ) {
                dialogBinding?.position =
                    (seekBar.progress * duration / 10000).toStringAsTime()
                dialogBinding?.duration = duration.toStringAsTime()
            }
        })
    }

    private fun showAudioDialog() {
        context?.let {
            if (dialog == null) {
                dialog = if (themeManager.currentTheme.id == Theme.CLASSIC) BottomSheetDialog(
                    it,
                    R.style.BottomSheetDialogTheme
                ) else BottomSheetDialog(it, R.style.BottomSheetDialogTheme)
                dialog?.setContentView(dialogBinding!!.root)
            }
            dialog?.show()
        }
    }

    private fun pause() {
        HalqaPlayer.pause()
        audioStatus = AudioBook.Status.Downloaded
        setIconsVisible()
    }

    private fun play() {
        HalqaPlayer.playOrResume(chapterId)
        audioStatus = AudioBook.Status.Playing(HalqaPlayer.position, HalqaPlayer.duration)
        setIconsVisible()
    }

    private fun download() {
        audioStatus = AudioBook.Status.Downloading(0, 1)
        setIconsVisible()
        DownloadManger.download(chapterId)
        dialog?.dismiss()
    }

    private fun next() {
        pause()
        chapterId++
        setData()

    }

    private fun prev() {
        pause()
        chapterId--
        setData()
    }

    private fun setData() {
        dialogBinding?.position = ""
        dialogBinding?.duration = ""
        viewModel.getChapter(chapterId)
        audioStatus =
            if (prefs.get(prefs.audioItemDownloaded + chapterId, false)) {
                if (HalqaPlayer.getPlayingId() == chapterId)
                    AudioBook.Status.Playing(HalqaPlayer.position, HalqaPlayer.duration)
                else AudioBook.Status.Downloaded
            } else AudioBook.Status.Online(0L)
        setIconsVisible()
    }
}