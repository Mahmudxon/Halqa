package uz.mahmudxon.halqa.domain.model

data class AudioBook(
    val id: Int,
    val title: String,
    var status: Status
) {
    sealed class Status {
        data class Online(var size: Long, val format: String = "M4A") : Status()
        data class Downloading(var current: Long = 0L, val total: Long) : Status()
        object Downloaded : Status()
        data class Playing(val position: Long = 0, val duration: Long = 0) : Status()
    }
}