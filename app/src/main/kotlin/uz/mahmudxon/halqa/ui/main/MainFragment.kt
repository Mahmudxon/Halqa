package uz.mahmudxon.halqa.ui.main

import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import uz.mahmudxon.halqa.R
import uz.mahmudxon.halqa.databinding.FragmentMainBinding
import uz.mahmudxon.halqa.domain.model.Chapter
import uz.mahmudxon.halqa.ui.base.BaseFragment
import uz.mahmudxon.halqa.ui.base.list.SingleTypeAdapter
import uz.mahmudxon.halqa.ui.list.ChaptersAdapter
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main),
    SingleTypeAdapter.OnItemClickListener<Chapter> {

    private val viewModel by viewModels<MainViewModel>()

    @Inject
    lateinit var adapter: ChaptersAdapter

    override fun onCreate(view: View) {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
        adapter.setItemClickListener(this)
        viewModel.getChaptersList()
        viewModel.chaptersState.observe(this) { state ->
            state.data?.let {
                adapter.swapData(it)
            }
        }
    }

    override fun onListItemClick(item: Chapter) {
        navController.navigate(
            R.id.action_mainFragment_to_storyFragment,
            bundleOf("id" to item.chapterNumber)
        )
    }
}