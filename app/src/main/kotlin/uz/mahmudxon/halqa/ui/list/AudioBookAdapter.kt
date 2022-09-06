package uz.mahmudxon.halqa.ui.list

import androidx.databinding.ViewDataBinding
import uz.mahmudxon.halqa.R
import uz.mahmudxon.halqa.databinding.ItemAudioBinding
import uz.mahmudxon.halqa.domain.model.AudioBook
import uz.mahmudxon.halqa.ui.base.list.SingleTypeAdapter
import uz.mahmudxon.halqa.util.theme.ThemeManager
import javax.inject.Inject

class AudioBookAdapter @Inject constructor(private val themeManager: ThemeManager) :
    SingleTypeAdapter<AudioBook>(R.layout.item_audio) {
    override fun bindData(binding: ViewDataBinding, position: Int) {
        if (binding is ItemAudioBinding) {
            binding.theme = themeManager.currentTheme
            with(data[position]) {
                binding.title.text = title
                binding.description.text = description
                binding.status = status
            }
        }
    }
}