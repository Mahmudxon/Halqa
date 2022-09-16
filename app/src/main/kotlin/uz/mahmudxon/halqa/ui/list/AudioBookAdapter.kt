package uz.mahmudxon.halqa.ui.list

import android.view.View
import androidx.databinding.ViewDataBinding
import uz.mahmudxon.halqa.R
import uz.mahmudxon.halqa.databinding.ItemAudioBinding
import uz.mahmudxon.halqa.domain.model.AudioBook
import uz.mahmudxon.halqa.ui.base.list.SingleTypeAdapter
import uz.mahmudxon.halqa.util.FontManager
import uz.mahmudxon.halqa.util.theme.ThemeManager
import uz.mahmudxon.halqa.util.toStringAsFileSize
import javax.inject.Inject

class AudioBookAdapter @Inject constructor(
    private val themeManager: ThemeManager,
    private val fontManager: FontManager
) : SingleTypeAdapter<AudioBook>(R.layout.item_audio) {

    var listener: ((Int) -> Unit)? = null

    override fun bindData(binding: ViewDataBinding, position: Int) {
        if (binding is ItemAudioBinding) {
            binding.theme = themeManager.currentTheme
            binding.fontSize = fontManager.fontSize
            binding.icon.setOnClickListener { listener?.invoke(data[position].id) }
            with(data[position]) {
                binding.title = title
                binding.progress.visibility =
                    if (status is AudioBook.Status.Downloading) View.VISIBLE else View.GONE
                when (val status = this.status) {
                    is AudioBook.Status.Online -> {
                        binding.icon.setImageResource(R.drawable.ic_download)
                        binding.description = "Aбдукарим Мирзаев"
                    }
                    is AudioBook.Status.Downloading -> {
                        binding.icon.setImageResource(R.drawable.ic_cancel)
                        binding.description =
                            status.current.toStringAsFileSize() + " / " + status.total.toStringAsFileSize()
                        binding.progress.progress = ((status.current * 100) / status.total).toInt()
                    }
                    is AudioBook.Status.Playing -> {
                        binding.icon.setImageResource(if (status.isPlaying) R.drawable.ic_pause else R.drawable.ic_play)
                        binding.description = "Aбдукарим Мирзаев"
                    }
                }
            }
        }
    }
}