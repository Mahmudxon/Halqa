package uz.mahmudxon.halqa.ui.author

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import dagger.hilt.android.AndroidEntryPoint
import uz.mahmudxon.halqa.R
import uz.mahmudxon.halqa.databinding.FragmentAuthorBinding
import uz.mahmudxon.halqa.domain.model.Author
import uz.mahmudxon.halqa.ui.base.BaseFragment
import uz.mahmudxon.halqa.ui.list.AuthorsAdapter
import uz.mahmudxon.halqa.util.TAG
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
        FirebaseDatabase.getInstance().reference.child("authors")
            .addValueEventListener(OnValueEventListener())
        binding.backButton.setOnClickListener { finish() }
    }

    override fun onCreateTheme(theme: Theme) {
        super.onCreateTheme(theme)
        binding.theme = theme
    }

    inner class OnValueEventListener : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // Get Post object and use the values to update the UI
            val authors = dataSnapshot.getValue<List<Author>>()
            authors?.let { adapter.swapData(it) }
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Post failed, log a message
            Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
        }
    }

}