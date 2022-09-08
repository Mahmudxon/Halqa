package uz.mahmudxon.halqa.domain.model

data class AudioBook(
    val id: Int,
    val title: String,
    var status: Status
) {
    sealed class Status {
        data class Online(var size: Long, val format: String = "M4A") : Status()
        data class Downloading(var current: Long = 0L, val total: Long) : Status()
        data class Playing(var isPlaying: Boolean = false) : Status()
    }
}