package uz.mahmudxon.halqa.ui.story

import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import uz.mahmudxon.halqa.R
import uz.mahmudxon.halqa.databinding.FragmentMainBinding
import uz.mahmudxon.halqa.databinding.FragmentStoryBinding
import uz.mahmudxon.halqa.ui.base.BaseFragment
import uz.mahmudxon.halqa.util.FontManager
import uz.mahmudxon.halqa.util.theme.Theme
import uz.mahmudxon.halqa.util.theme.ThemeManager
import javax.inject.Inject

@AndroidEntryPoint
class StoryFragment : BaseFragment<FragmentStoryBinding>(R.layout.fragment_story) {

    private val viewModel by viewModels<StoryViewModel>()

    @Inject
    lateinit var fontManager: FontManager

    override fun onCreate(view: View) {
        val id = arguments?.getInt("id")
        id?.let { viewModel.getChapter(it) }
        binding.backButton.setOnClickListener { finish() }
        binding.isPlaying = true
        binding.chapterLayout.fontSize = fontManager.fontSize
        viewModel.chapter.observe(this)
        { state->
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
}