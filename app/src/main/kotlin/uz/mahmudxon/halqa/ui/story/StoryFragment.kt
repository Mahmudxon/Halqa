package uz.mahmudxon.halqa.ui.story

import android.view.View
import android.view.animation.AnimationUtils
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
import javax.inject.Inject

@AndroidEntryPoint
class StoryFragment : BaseFragment<FragmentStoryBinding>(R.layout.fragment_story),
    DownloadManger.OnDownloadListener {

    private val viewModel by viewModels<StoryViewModel>()

    @Inject
    lateinit var fontManager: FontManager

    @Inject
    lateinit var prefs: Prefs

    var chapterId: Int = 0
    lateinit var audioStatus: AudioBook.Status

    override fun onCreate(view: View) {
        chapterId = arguments?.getInt("id") ?: -1
        viewModel.getChapter(chapterId)
        audioStatus =
            if (prefs.get(prefs.audioItemDownloaded + chapterId, false)) AudioBook.Status.Playing(
                isPlaying = HalqaPlayer.getPlayingId() == chapterId
            )
            else AudioBook.Status.Online(0L)
        setIconsVisible()
        DownloadManger.setListener(this)
        binding.play.setOnClickListener {
            HalqaPlayer.playOrResume(chapterId)
            audioStatus = AudioBook.Status.Playing(true)
            setIconsVisible()
        }
        binding.playing.setOnClickListener {
            HalqaPlayer.pause()
            audioStatus = AudioBook.Status.Playing(false)
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
            audioStatus = AudioBook.Status.Playing()
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
                binding.play.visibility =
                    if ((audioStatus as AudioBook.Status.Playing).isPlaying) View.GONE else View.VISIBLE
                binding.playing.visibility =
                    if ((audioStatus as AudioBook.Status.Playing).isPlaying) View.VISIBLE else View.GONE
            }
        }
    }
}