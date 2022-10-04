package uz.mahmudxon.halqa.ui.author

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import uz.mahmudxon.halqa.R
import uz.mahmudxon.halqa.databinding.FragmentAuthorBinding
import uz.mahmudxon.halqa.ui.base.BaseFragment
import uz.mahmudxon.halqa.ui.list.AuthorsAdapter
import uz.mahmudxon.halqa.util.getAuthors
import uz.mahmudxon.halqa.util.theme.Theme
import javax.inject.Inject

@AndroidEntryPoint
class AuthorFragment : BaseFragment<FragmentAuthorBinding>(R.layout.fragment_author) {

    @Inject
    lateinit var adapter: AuthorsAdapter

    override fun onCreate(view: View) {
        binding.list.layoutManager = LinearLayoutManager(context)
        binding.list.adapter = adapter
        adapter.swapData(getAuthors())
    }

    override fun onCreateTheme(theme: Theme) {
        super.onCreateTheme(theme)
        binding.theme = theme
    }
}