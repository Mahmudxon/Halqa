package uz.mahmudxon.halqa.util

import android.content.res.ColorStateList
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.util.TypedValue
import android.view.View
import android.widget.*
import androidx.annotation.ColorRes
import androidx.appcompat.widget.SearchView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.SimpleColorFilter
import com.airbnb.lottie.model.KeyPath
import com.airbnb.lottie.value.LottieValueCallback
import com.google.android.material.progressindicator.CircularProgressIndicator
import uz.mahmudxon.halqa.util.theme.Theme

@BindingAdapter("backgroundColor")
fun setBackgroundColor(view: View, colorId: Int) {
    if (colorId == 0)
        return
    val color = ContextCompat.getColor(view.context, colorId)
    view.setBackgroundColor(color)
}

@BindingAdapter("iconColor")
fun setIconColor(view: ImageView, @ColorRes colorId: Int) {
    if (colorId == 0) {
        view.colorFilter = null
        return
    }
    val color = ContextCompat.getColor(view.context, colorId)
    view.setColorFilter(color, PorterDuff.Mode.SRC_IN)
}

@BindingAdapter("textColor")
fun setTextColor(view: TextView, @ColorRes colorId: Int) {
    if (colorId == 0) return
    val color = ContextCompat.getColor(view.context, colorId)
    view.setTextColor(color)
}

@BindingAdapter("textColor")
fun setTextColor(view: Button, @ColorRes colorId: Int) {
    if (colorId == 0) return
    val color = ContextCompat.getColor(view.context, colorId)
    view.setTextColor(color)
}

@BindingAdapter("textColor")
fun setTextColor(view: EditText, @ColorRes colorId: Int) {
    if (colorId == 0) return
    val color = ContextCompat.getColor(view.context, colorId)
    view.setTextColor(color)
}

@BindingAdapter("hintTextColor")
fun setHintColor(view: EditText, @ColorRes colorId: Int) {
    if (colorId == 0) return
    val color = ContextCompat.getColor(view.context, colorId)
    view.setHintTextColor(color)
}

@BindingAdapter("progressColor")
fun setProgressColor(progressBar: ProgressBar, colorId: Int) {
    if (colorId == 0) return
    val color = ContextCompat.getColor(progressBar.context, colorId)
    progressBar.indeterminateDrawable?.colorFilter =
        PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)
}

@BindingAdapter("progressColor")
fun setProgressColor(progressBar: CircularProgressIndicator, colorId: Int) {
    if (colorId == 0) return
    val color = ContextCompat.getColor(progressBar.context, colorId)
    progressBar.setIndicatorColor(color)
}

@BindingAdapter("radioTheme")
fun setRadioTheme(radioButton: RadioButton, theme: Theme?) {
    theme?.let {
        val checked = ContextCompat.getColor(radioButton.context, theme.primaryTextColor)
        val unchecked = ContextCompat.getColor(radioButton.context, theme.secondaryTextColor)
        val states = arrayOf(
            intArrayOf(android.R.attr.state_checked),
            intArrayOf(-android.R.attr.state_checked)
        )

        val colors = intArrayOf(
            checked, unchecked
        )

        radioButton.buttonTintList = ColorStateList(states, colors)
    }
}

@BindingAdapter("searchViewTheme")
fun setSearchTheme(searchView: SearchView, theme: Theme?) {
    theme?.let {
        val defTextColor = ContextCompat.getColor(searchView.context, it.primaryTextColor)
        searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_button)
            ?.setColorFilter(defTextColor, android.graphics.PorterDuff.Mode.SRC_IN)
        searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)
            ?.setColorFilter(defTextColor, android.graphics.PorterDuff.Mode.SRC_IN)
        searchView.findViewById<TextView>(androidx.appcompat.R.id.search_src_text)
            ?.setTextColor(defTextColor)
    }
}

@BindingAdapter("visibility")
fun setVisibility(view: View, visible: Boolean) {
    view.visibility = if (!visible) View.GONE else View.VISIBLE
}

@BindingAdapter("cardBackColor")
fun setCardBack(view: CardView, colorId: Int) {
    if (colorId == 0) return
    val color = ContextCompat.getColor(view.context, colorId)
    view.setCardBackgroundColor(color)
}

@BindingAdapter("adapter")
fun setAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) {
    view.adapter = adapter
}

@BindingAdapter("onClick")
fun setOnClickListener(view: View, onClick: View.OnClickListener?) {
    onClick?.let {
        view.setOnClickListener(it)
    }
}

@BindingAdapter("layerColor")
fun changeLayersColor(
    animationView: LottieAnimationView,
    @ColorRes colorRes: Int
) {
    val color = ContextCompat.getColor(animationView.context, colorRes)
    val filter = SimpleColorFilter(color)
    val keyPath = KeyPath("**")
    val callback: LottieValueCallback<ColorFilter> = LottieValueCallback(filter)

    animationView.addValueCallback(keyPath, LottieProperty.COLOR_FILTER, callback)
}


@BindingAdapter("textSize")
fun setTextSize(view: TextView, textSize: Int) {
    view.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize.toFloat())
}
