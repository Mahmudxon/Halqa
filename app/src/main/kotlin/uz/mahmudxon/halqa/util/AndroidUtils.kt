package uz.mahmudxon.halqa.util

import android.content.res.Resources
import uz.mahmudxon.halqa.domain.model.AudioBook

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


fun getFakeList(): ArrayList<AudioBook> = ArrayList<AudioBook>().also {

    it.add(
        AudioBook(
            id = 1,
            title = "1 - bob",
            status = AudioBook.Status.Online(125612L)
        )
    )
    it.add(
        AudioBook(
            id = 1,
            title = "2 - bob",
            status = AudioBook.Status.Downloading(51272L, 372832L)
        )
    )
    it.add(
        AudioBook(
            id = 1,
            title = "3 - bob",
            status = AudioBook.Status.Playing()
        )
    )

}