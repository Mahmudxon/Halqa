package uz.mahmudxon.halqa.player

import android.app.Application
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import uz.mahmudxon.halqa.datasource.network.AudioUrl

object HalqaPlayer : Player.Listener {
    private lateinit var player: ExoPlayer
    private var audioBookId = -1
    private var timer: Job? = null

    var listener: PlayerListener? = null

    fun init(context: Application) {
        player = ExoPlayer.Builder(context).build()
    }

    fun isPlaying() = player.isPlaying

    var position: Long
        get() = player.contentPosition
        set(value) {
            seek(value)
        }

    val duration: Long
        get() = if (player.duration > 1000) player.duration else 0

    fun seek(position: Long) {
        if (position < player.duration)
            player.seekTo(position)
    }

    fun playOrResume(id: Int) {
        if (this.audioBookId != id) {
            this.audioBookId = id
            val mediaItem: MediaItem = MediaItem.fromUri(AudioUrl.offLineUrl(id))
            player.setMediaItem(mediaItem)
            player.prepare()
        }
        listener?.onPlaying(id, position, duration)
        player.play()
        timer = CoroutineScope(IO).launch {
            tick()
        }
    }

    fun pause() {
        player.pause()
        timer?.cancel()
    }

    fun getPlayingId(): Int {
        return if (player.isPlaying)
            audioBookId
        else -1
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        super.onPlaybackStateChanged(playbackState)
        if (playbackState == ExoPlayer.STATE_ENDED) {
            listener?.onTrackEnded(audioBookId)
            timer?.cancel()
        }

    }


    private suspend fun tick() {
        delay(1000)
        var playing: Boolean
        withContext(Main) {
            listener?.onPlaying(
                audioBookId,
                position,
                duration
            )
            playing = player.isPlaying
        }
        if (playing)
            tick()
    }

    interface PlayerListener {
        fun onTrackEnded(id: Int)
        fun onPlaying(id: Int, position: Long, duration: Long)
    }
}