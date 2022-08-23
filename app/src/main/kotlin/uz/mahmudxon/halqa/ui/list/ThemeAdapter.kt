package uz.mahmudxon.halqa.ui.list

import androidx.databinding.ViewDataBinding
import uz.mahmudxon.halqa.R
import uz.mahmudxon.halqa.databinding.ItemThemeBinding
import uz.mahmudxon.halqa.ui.base.list.SingleTypeAdapter
import uz.mahmudxon.halqa.util.theme.Theme
import uz.mahmudxon.halqa.util.theme.ThemeManager
import javax.inject.Inject

class ThemeAdapter @Inject constructor(private val themeManager: ThemeManager) :
    SingleTypeAdapter<Theme>(R.layout.item_theme, data = ArrayList<Theme>().also { it.addAll(themeManager.themes) }) {
    override fun bindData(binding: ViewDataBinding, position: Int) {
        if (binding is ItemThemeBinding) {
            binding.theme = data[position]
        }
    }
}