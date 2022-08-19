package uz.mahmudxon.halqa.ui.base.list

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import uz.mahmudxon.halqa.R

abstract class SingleTypeAdapter<T>(
    @LayoutRes val layout: Int, var data: ArrayList<T> = ArrayList(),
    var context: Context? = null
) : RecyclerView.Adapter<SingleTypeAdapter<T>.ViewHolder>() {
    private var lastPosition = -1
    private var _listener: OnItemClickListener<T>? = null
    fun setItemClickListener(listener: OnItemClickListener<T>) {
        _listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val dataBinding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layout,
            parent,
            false
        )

        if (context == null)
            context = parent.context
        return ViewHolder(dataBinding)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun swapData(newData: List<T>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearData() {
        data.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    abstract fun bindData(binding: ViewDataBinding, position: Int)


    inner class ViewHolder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.root.setOnClickListener {
                _listener?.onListItemClick(data[adapterPosition])
            }
            bindData(binding, adapterPosition)
        }

        private fun setAnimation() {
            val animation = AnimationUtils.loadAnimation(itemView.context, android.R.anim.slide_in_left)
            itemView.startAnimation(animation)
            lastPosition = adapterPosition
        }

    }

    interface OnItemClickListener<T> {
        fun onListItemClick(item: T)
    }
}
