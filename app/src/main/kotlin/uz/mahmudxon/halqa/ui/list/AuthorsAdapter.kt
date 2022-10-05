package uz.mahmudxon.halqa.ui.list

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import uz.mahmudxon.halqa.R
import uz.mahmudxon.halqa.databinding.ItemAuthorBinding
import uz.mahmudxon.halqa.domain.model.Author
import uz.mahmudxon.halqa.ui.base.list.SingleTypeAdapter
import uz.mahmudxon.halqa.util.FontManager
import uz.mahmudxon.halqa.util.theme.ThemeManager
import javax.inject.Inject

class AuthorsAdapter @Inject constructor(
    private val themeManager: ThemeManager,
    private val fontManager: FontManager
) : SingleTypeAdapter<Author>(R.layout.item_author) {
    override fun bindData(binding: ViewDataBinding, position: Int) {
        if (binding is ItemAuthorBinding) {
            with(data[position])
            {
                binding.title = name
                binding.jobTitle = jobTitle
                binding.description = description
                binding.listLinks.layoutManager =
                    LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
                binding.listLinks.adapter = SocialMediaAdapter(socialMediaLinks)
                binding.theme = themeManager.currentTheme
                binding.fontSize = fontManager.fontSize
                Glide.with(binding.image).load(imgUrl).error(getDefaultImage(id))
                    .into(binding.image)
            }
        }
    }
}