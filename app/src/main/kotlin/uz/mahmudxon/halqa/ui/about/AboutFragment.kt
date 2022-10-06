package uz.mahmudxon.halqa.ui.about

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
import uz.mahmudxon.halqa.databinding.FragmentAboutBinding
import uz.mahmudxon.halqa.domain.model.ShopLink
import uz.mahmudxon.halqa.ui.base.BaseFragment
import uz.mahmudxon.halqa.ui.list.ShopLinkAdapter
import uz.mahmudxon.halqa.util.FontManager
import uz.mahmudxon.halqa.util.TAG
import uz.mahmudxon.halqa.util.theme.Theme
import javax.inject.Inject

@AndroidEntryPoint
class AboutFragment : BaseFragment<FragmentAboutBinding>(R.layout.fragment_about) {

    @Inject
    lateinit var fontManager: FontManager

    override fun onCreate(view: View) {
        binding.backButton.setOnClickListener { finish() }
        binding.fontSize = fontManager.fontSize
        binding.listLinks.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        FirebaseDatabase.getInstance().reference.child("shops")
            .addValueEventListener(OnValueEventListener())
    }

    override fun onCreateTheme(theme: Theme) {
        super.onCreateTheme(theme)
        binding.theme = theme
    }

    inner class OnValueEventListener : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // Get Post object and use the values to update the UI
            val shops = dataSnapshot.getValue<List<ShopLink>>()
            shops?.let { binding.listLinks.adapter =  ShopLinkAdapter(it) }
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Post failed, log a message
            Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
        }
    }

}