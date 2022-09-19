package uz.mahmudxon.halqa.player

import android.app.Application
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import uz.mahmudxon.halqa.datasource.network.AudioUrl

object Player {
    private lateinit var player: ExoPlayer
    private var id = -1

    fun init(context: Application) {
        player = ExoPlayer.Builder(context).build()
    }

    fun play(id: Int) {
        this.id = id
        val mediaItem: MediaItem = MediaItem.fromUri(AudioUrl.offLineUrl(id))
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    }

    fun pause() {
        player.pause()
    }

    fun getPlayingId(): Int {
        return if (player.isPlaying)
            id
        else -1
    }
}