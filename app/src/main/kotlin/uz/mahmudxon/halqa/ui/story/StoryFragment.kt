package uz.mahmudxon.halqa.ui.story

import android.view.View
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
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

    override fun onCreate(view: View) {
        chapterId = arguments?.getInt("id") ?: -1
        viewModel.getChapter(chapterId)
        HalqaPlayer.listener = this
        audioStatus =
            if (prefs.get(prefs.audioItemDownloaded + chapterId, false)) {
                if (HalqaPlayer.getPlayingId() == chapterId)
                    AudioBook.Status.Playing(HalqaPlayer.position, HalqaPlayer.duration)
                else AudioBook.Status.Downloaded
            } else AudioBook.Status.Online(0L)
        setIconsVisible()
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
        viewModel.chapter.observe(this)
        { state ->
            state.data?.let {
                binding.chapterLayout.chapter = it
                binding.title.text = it.title
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
                binding.playing.visibility =
                    View.VISIBLE
                binding.play.visibility = View.GONE
            }
            is AudioBook.Status.Downloaded -> {
                binding.download.visibility = View.GONE
                binding.downloading.visibility = View.GONE
                binding.play.visibility = View.VISIBLE
                binding.playing.visibility = View.GONE
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
    }
}