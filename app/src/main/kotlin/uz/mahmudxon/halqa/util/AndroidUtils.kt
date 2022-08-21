package uz.mahmudxon.halqa.util

import android.content.res.Resources

val Int.dp: Int
    get() =
        (this * Resources.getSystem().displayMetrics.density).toInt()