package uz.mahmudxon.halqa.domain.model

data class AudioBook(
    val id: Int,
    val title: String,
    val description: String,
    var status: Int
) {
    object Status {
        const val ONLINE = 0
        const val DOWNLOADING = 1
        const val DOWNLOADED = 2
    }
}