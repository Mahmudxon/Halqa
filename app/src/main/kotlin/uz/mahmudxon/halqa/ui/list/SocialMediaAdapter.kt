package uz.mahmudxon.halqa.ui.list

import android.content.Intent
import android.net.Uri
import androidx.databinding.ViewDataBinding
import uz.mahmudxon.halqa.R
import uz.mahmudxon.halqa.databinding.ItemSocialMediaBinding
import uz.mahmudxon.halqa.domain.model.SocialMediaLink
import uz.mahmudxon.halqa.ui.base.list.SingleTypeAdapter
import uz.mahmudxon.halqa.util.asArrayList

class SocialMediaAdapter(data: List<SocialMediaLink>) :
    SingleTypeAdapter<SocialMediaLink>(R.layout.item_social_media, data = data.asArrayList()) {
    override fun bindData(binding: ViewDataBinding, position: Int) {
        if (binding is ItemSocialMediaBinding) {
            binding.icon.setImageResource(data[position].icon)
            binding.root.setOnClickListener {
                val intent = Intent()
                intent.data = Uri.parse(data[position].link)
                it.context.startActivity(intent)
            }
        }
    }
}