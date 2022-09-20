package uz.mahmudxon.halqa.app

import android.app.Application
import android.content.Intent
import dagger.hilt.android.HiltAndroidApp
import uz.mahmudxon.halqa.datasource.network.AudioUrl
import uz.mahmudxon.halqa.player.HalqaPlayer
import uz.mahmudxon.halqa.ui.error.ErrorActivity
import uz.mahmudxon.halqa.util.logd
import kotlin.system.exitProcess

@HiltAndroidApp
class Halqa : Application() {
    override fun onCreate() {
        super.onCreate()
        AudioUrl.init(this)
        HalqaPlayer.init(this)
        Thread.setDefaultUncaughtExceptionHandler { _, _ ->
            try {
                val flags = Intent.FLAG_ACTIVITY_NEW_TASK
                val i = Intent(this, ErrorActivity::class.java)
                i.addFlags(flags)
                startActivity(i)
                exitProcess(1)
            } catch (e: Exception) {
                e.message?.let { logd(it) }
            }
        }
    }
}