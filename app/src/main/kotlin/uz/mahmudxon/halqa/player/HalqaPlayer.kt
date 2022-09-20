package uz.mahmudxon.halqa.player

import android.app.Application
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import uz.mahmudxon.halqa.datasource.network.AudioUrl

object HalqaPlayer : Player.Listener  {
    private lateinit var player: ExoPlayer
    private var id = -1

    fun init(context: Application) {
        player = ExoPlayer.Builder(context).build()
    }

    fun playOrResume(id: Int) {
        if(this.id != id)
        {
            this.id = id
            val mediaItem: MediaItem = MediaItem.fromUri(AudioUrl.offLineUrl(id))
            player.setMediaItem(mediaItem)
            player.prepare()
        }

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

    override fun onPlaybackStateChanged(playbackState: Int) {
        super.onPlaybackStateChanged(playbackState)
        if (playbackState == ExoPlayer.STATE_ENDED) {

        }
    }
}