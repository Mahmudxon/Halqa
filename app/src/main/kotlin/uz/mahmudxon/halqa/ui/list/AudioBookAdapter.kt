package uz.mahmudxon.halqa.ui.list

import android.view.View
import android.view.animation.AnimationUtils
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.databinding.ViewDataBinding
import uz.mahmudxon.halqa.R
import uz.mahmudxon.halqa.databinding.ItemAudioBinding
import uz.mahmudxon.halqa.domain.model.AudioBook
import uz.mahmudxon.halqa.player.HalqaPlayer
import uz.mahmudxon.halqa.ui.base.list.SingleTypeAdapter
import uz.mahmudxon.halqa.util.FontManager
import uz.mahmudxon.halqa.util.theme.ThemeManager
import uz.mahmudxon.halqa.util.toStringAsFileSize
import uz.mahmudxon.halqa.util.toStringAsTime
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
                        if (status.size == 0L)
                            binding.description = "Aбдукарим Мирзаев"
                        else binding.description =
                            status.size.toStringAsFileSize() + " | " + status.format
                    }
                    is AudioBook.Status.Downloading -> {
                        binding.progress.animation =
                            AnimationUtils.loadAnimation(binding.progress.context, R.anim.rotate)
                        binding.progress.animate()
                        binding.icon.setImageResource(R.drawable.ic_cancel)
                        binding.description =
                            status.current.toStringAsFileSize() + " / " + status.total.toStringAsFileSize()
                        binding.progress.progress =
                            ((status.current * 100) / status.total).toInt() + 2
                    }
                    is AudioBook.Status.Playing -> {
                        binding.isPlaying = true
                        binding.icon.setImageResource(R.drawable.ic_pause)
                        binding.description =
                            status.position.toStringAsTime() + " / " + status.duration.toStringAsTime()
                    }
                    is AudioBook.Status.Downloaded -> {
                        binding.isPlaying = false
                        binding.icon.setImageResource(R.drawable.ic_play)
                        binding.description = "Aбдукарим Мирзаев"
                    }
                }
            }
        }
    }

    override fun onBindViewHolder(
        holder: SingleTypeAdapter<AudioBook>.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val status = payloads[0]
            val binding = holder.binding
            if (binding is ItemAudioBinding) {
                binding.icon.setOnClickListener { listener?.invoke(data[position].id) }
                binding.progress.visibility =
                    if (status is AudioBook.Status.Downloading) View.VISIBLE else View.GONE
                when (status) {
                    /*is AudioBook.Status.Online -> {
                        binding.icon.setImageResource(R.drawable.ic_download)
                        if (status.size == 0L)
                            binding.description = "Aбдукарим Мирзаев"
                        else binding.description =
                            status.size.toStringAsFileSize() + " | " + status.format
                    }*/
                    is AudioBook.Status.Downloading -> {
                       // binding.icon.setImageResource(R.drawable.ic_cancel)
                        binding.description =
                            status.current.toStringAsFileSize() + " / " + status.total.toStringAsFileSize()
                        binding.progress.progress =
                            ((status.current * 100) / status.total).toInt() + 10
                    }
                    is AudioBook.Status.Playing -> {
                      //  binding.icon.setImageResource(R.drawable.ic_pause)
                        binding.seekBar.progress = (status.position * 10000 / status.duration).toInt()
                        binding.seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
                            override fun onStopTrackingTouch(seekBar: SeekBar) {
                                HalqaPlayer.seek(seekBar.progress * status.duration / 10000)
                            }
                            override fun onStartTrackingTouch(seekBar: SeekBar) {}
                            override fun onProgressChanged(
                                seekBar: SeekBar,
                                progress: Int,
                                fromUser: Boolean
                            ) {
                                binding.description =
                                    (seekBar.progress * status.duration / 10000).toStringAsTime() + " / " + status.duration.toStringAsTime()
                            }
                        })
                    }
                    is AudioBook.Status.Downloaded -> {
                        binding.icon.setImageResource(R.drawable.ic_play)
                        binding.description = "Aбдукарим Мирзаев"
                        binding.isPlaying = false
                    }
                }
            }
        }
    }

}