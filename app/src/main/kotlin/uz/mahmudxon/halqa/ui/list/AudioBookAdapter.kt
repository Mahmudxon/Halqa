package uz.mahmudxon.halqa.ui.list

import androidx.databinding.ViewDataBinding
import uz.mahmudxon.halqa.R
import uz.mahmudxon.halqa.databinding.ItemAudioBinding
import uz.mahmudxon.halqa.domain.model.AudioBook
import uz.mahmudxon.halqa.ui.base.list.SingleTypeAdapter
import uz.mahmudxon.halqa.util.FontManager
import uz.mahmudxon.halqa.util.theme.ThemeManager
import javax.inject.Inject

class AudioBookAdapter @Inject constructor(
    private val themeManager: ThemeManager,
    private val fontManager: FontManager
) :
    SingleTypeAdapter<AudioBook>(R.layout.item_audio) {
    override fun bindData(binding: ViewDataBinding, position: Int) {
        if (binding is ItemAudioBinding) {
            binding.theme = themeManager.currentTheme
            binding.fontSize = fontManager.fontSize
            with(data[position]) {
                binding.title = title
                when (val status = this.status) {
                    is AudioBook.Status.Online -> binding.icon.setImageResource(R.drawable.ic_download)
                    is AudioBook.Status.Downloading -> {
                        binding.icon.setImageResource(R.drawable.ic_cancel)
                    }
                    is AudioBook.Status.Playing -> {
                        binding.icon.setImageResource(if (status.isPlaying) R.drawable.ic_pause else R.drawable.ic_play)
                    }
                }
            }
        }
    }
}