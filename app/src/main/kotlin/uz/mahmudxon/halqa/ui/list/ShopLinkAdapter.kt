package uz.mahmudxon.halqa.ui.list

import android.content.Intent
import android.net.Uri
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import uz.mahmudxon.halqa.R
import uz.mahmudxon.halqa.databinding.ItemSocialMediaBinding
import uz.mahmudxon.halqa.domain.model.ShopLink
import uz.mahmudxon.halqa.ui.base.list.SingleTypeAdapter
import uz.mahmudxon.halqa.util.asArrayList

class ShopLinkAdapter(data: List<ShopLink>) :
    SingleTypeAdapter<ShopLink>(R.layout.item_social_media, data = data.asArrayList()) {
    override fun bindData(binding: ViewDataBinding, position: Int) {
        if (binding is ItemSocialMediaBinding) {
            Glide.with(binding.icon).load(data[position].icon).error(R.drawable.ic_shopping_mall)
                .into(binding.icon)
            binding.root.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(data[position].link)
                it.context.startActivity(intent)
            }
        }
    }
}