package uz.mahmudxon.halqa.app

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import dagger.hilt.android.AndroidEntryPoint
import uz.mahmudxon.halqa.R
import uz.mahmudxon.halqa.ui.MainActivity
import uz.mahmudxon.halqa.util.Prefs
import uz.mahmudxon.halqa.util.TAG
import javax.inject.Inject


@AndroidEntryPoint
class FCM : FirebaseMessagingService() {

    @Inject
    lateinit var prefs: Prefs

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        prefs.save(prefs.token, token)
        Log.d(TAG, "onNewToken: $token")
    }

    override fun handleIntent(p0: Intent) {
        val bundle = p0.extras
        bundle?.getString("token")?.let { token ->
            prefs.save(prefs.token, token)
            Log.d(TAG, "onNewToken: $token")
            return
        }

        Log.d(TAG, "handleIntent: $bundle")
        val body = bundle?.getString("gcm.notification.body")
        val title = bundle?.getString("gcm.notification.title")
        showNotification(title ?: "", body ?: "")
    }

    private fun showNotification(title: String, body: String) {
        val channelId = "channel1"
        val builder = NotificationCompat.Builder(this, channelId)

        builder
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)


        val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(channelId, "Yangiliklar", NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.setSound(uri, null)
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        val n = builder.build()
        notificationManager.notify(0, n)
    }


}