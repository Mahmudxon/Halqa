package uz.mahmudxon.halqa.ui.main

import android.content.res.ColorStateList
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.setPadding
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint
import uz.mahmudxon.halqa.R
import uz.mahmudxon.halqa.databinding.FragmentMainBinding
import uz.mahmudxon.halqa.databinding.FragmentSettingsBinding
import uz.mahmudxon.halqa.domain.model.Chapter
import uz.mahmudxon.halqa.ui.base.BaseFragment
import uz.mahmudxon.halqa.ui.base.list.SingleTypeAdapter
import uz.mahmudxon.halqa.ui.list.ChaptersAdapter
import uz.mahmudxon.halqa.ui.list.ThemeAdapter
import uz.mahmudxon.halqa.util.dp
import uz.mahmudxon.halqa.util.theme.Theme
import javax.inject.Inject


@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main),
    SingleTypeAdapter.OnItemClickListener<Chapter>, NavigationBarView.OnItemSelectedListener  {

    private val viewModel by viewModels<MainViewModel>()

    @Inject
    lateinit var chaptersAdapter: ChaptersAdapter

    @Inject
    lateinit var themeAdapter: ThemeAdapter

    lateinit var settingBinding : FragmentSettingsBinding

    override fun onCreate(view: View) {
        settingBinding = FragmentSettingsBinding.inflate(layoutInflater)
        chaptersAdapter.setItemClickListener(this)
        binding.viewPager.adapter = ViewpagerAdapter()
        binding.viewPager.offscreenPageLimit = 3
        binding.viewPager.isUserInputEnabled = false

        binding.bottomNavigation.setOnItemSelectedListener(this)
        viewModel.getChaptersList()
        viewModel.chaptersState.observe(this) { state ->
            state.data?.let {
                chaptersAdapter.swapData(it)
            }
        }
    }

    override fun onListItemClick(item: Chapter) {
        navController.navigate(
            R.id.action_mainFragment_to_storyFragment,
            bundleOf("id" to item.chapterNumber)
        )
    }

    override fun onCreateTheme(theme: Theme) {
        super.onCreateTheme(theme)
        binding.theme = theme
        settingBinding.theme = theme
        val iconColorStates = ColorStateList(
            arrayOf(
                intArrayOf(-android.R.attr.state_checked),
                intArrayOf(android.R.attr.state_checked)
            ), intArrayOf(
                ContextCompat.getColor(requireContext(), theme.secondaryTextColor),
                ContextCompat.getColor(requireContext(), theme.primaryTextColor)
            )
        )
        binding.bottomNavigation.itemIconTintList = iconColorStates
        binding.bottomNavigation.itemTextColor = iconColorStates
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
       return when (item.itemId) {
            R.id.chapter -> {
                binding.viewPager.currentItem = 0
                true
            }
            R.id.audio -> {
                binding.viewPager.currentItem = 1
                true
            }
            R.id.settings -> {
                binding.viewPager.currentItem = 2
                true
            }
            else -> false
        }
    }

    private inner class ViewpagerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        private var home : Int = -1
        private var audio : Int = -1
        private var settings : Int = -1

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view: View = when (viewType) {
                home -> {
                    val list = RecyclerView(parent.context)
                    list.layoutManager = LinearLayoutManager(parent.context)
                    list.adapter = chaptersAdapter
                    list.setPadding(8.dp)
                    list
                }
                settings -> settingBinding.root
                else -> {
                    View(parent.context)
                }
            }
            view.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
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
}