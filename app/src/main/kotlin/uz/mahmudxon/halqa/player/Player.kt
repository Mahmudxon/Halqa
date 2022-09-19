package uz.mahmudxon.halqa.player

import android.annotation.SuppressLint
import android.app.Application
import androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC
import androidx.core.app.NotificationCompat.VISIBILITY_SECRET
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import uz.mahmudxon.halqa.datasource.network.AudioUrl

object Player {
    private lateinit var player: ExoPlayer
    private var id = -1

    @SuppressLint("StaticFieldLeak")
    private var playerNotificationManager: PlayerNotificationManager? = null

    fun init(context: Application) {
        playerNotificationManager =
            PlayerNotificationManager.Builder(context, 70298, "HalqaPlayer").build()
        player = ExoPlayer.Builder(context).build()
        playerNotificationManager?.setPlayer(player)

    }

    fun play(id: Int) {
        this.id = id
        val mediaItem: MediaItem = MediaItem.fromUri(AudioUrl.offLineUrl(id))
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
        playerNotificationManager?.setVisibility(VISIBILITY_PUBLIC)
    }

    fun pause() {
        player.pause()
        playerNotificationManager?.setVisibility(VISIBILITY_SECRET)
    }

    fun getPlayingId(): Int {
        return if (player.isPlaying)
            id
        else -1
    }
}