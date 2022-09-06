package uz.mahmudxon.halqa.util

import android.content.res.Resources
import uz.mahmudxon.halqa.domain.model.AudioBook

val Int.dp: Int
    get() =
        (this * Resources.getSystem().displayMetrics.density).toInt()

fun getFakeList(): ArrayList<AudioBook> = ArrayList<AudioBook>().also {

    it.add(
        AudioBook(
            id = 1,
            title = "1 - bob",
            description = "24 mb | m4a",
            status = AudioBook.Status.ONLINE
        )
    )
    it.add(
        AudioBook(
            id = 1,
            title = "2 - bob",
            description = "18 mb | m4a",
            status = AudioBook.Status.DOWNLOADING
        )
    )
    it.add(
        AudioBook(
            id = 1,
            title = "3 - bob",
            description = "20 mb | m4a",
            status = AudioBook.Status.DOWNLOADED
        )
    )
    it.add(
        AudioBook(
            id = 1,
            title = "4 - bob",
            description = "28 mb | m4a",
            status = AudioBook.Status.ONLINE
        )
    )
    it.add(
        AudioBook(
            id = 1,
            title = "5 - bob",
            description = "32 mb | m4a",
            status = AudioBook.Status.ONLINE
        )
    )

}