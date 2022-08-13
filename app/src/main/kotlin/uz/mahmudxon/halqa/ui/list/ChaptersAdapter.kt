package uz.mahmudxon.halqa.ui.list

import androidx.databinding.ViewDataBinding
import uz.mahmudxon.halqa.R
import uz.mahmudxon.halqa.databinding.ItemChapterBinding
import uz.mahmudxon.halqa.domain.model.Chapter
import uz.mahmudxon.halqa.ui.base.list.SingleTypeAdapter
import uz.mahmudxon.halqa.util.theme.ThemeManager
import javax.inject.Inject

class ChaptersAdapter @Inject constructor(private val themeManager: ThemeManager) :
    SingleTypeAdapter<Chapter>(R.layout.item_chapter) {
    override fun bindData(binding: ViewDataBinding, position: Int) {
        if (binding is ItemChapterBinding) {
            binding.chapter = data[position]
            binding.theme = themeManager.currentTheme
            binding.progress = position
        }
    }
}