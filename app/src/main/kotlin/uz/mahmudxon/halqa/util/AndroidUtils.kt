package uz.mahmudxon.halqa.util

import android.content.res.Resources
import java.util.concurrent.TimeUnit


val Int.dp: Int
    get() =
        (this * Resources.getSystem().displayMetrics.density).toInt()


fun Long.toStringAsFileSize(): String {
    var temp = this.toFloat()
    if (temp < 1024)
        return "${temp.toInt()} B"

    temp /= 1024

    if (temp < 1024)
        return "%.2f KB".format(temp)
    temp /= 1024

    if (temp < 1024)
        return "%.2f MB".format(temp)
    temp /= 1024
    return "%.2f GB".format(temp)
}

fun Long.toStringAsTime(): String {
    val minutes: Long = TimeUnit.MILLISECONDS.toMinutes(this)
    val seconds: Long = TimeUnit.MILLISECONDS.toSeconds(this) - minutes * 60
    return "%02d:%02d".format(minutes, seconds)
}