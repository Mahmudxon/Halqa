package uz.mahmudxon.halqa.datasource.db.audio

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "AudioBook")
data class AudioEntity(
    @PrimaryKey
    val storyId: Int,
    var size: Long = 0,
    var isDownloaded: Boolean = false,
    var currentDownloadSize: Long = -1
)