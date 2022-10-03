package uz.mahmudxon.halqa.app

import android.app.Application
import android.content.Intent
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.get
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
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
        getRemoteConfig()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
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

    private fun getRemoteConfig() {
        val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val baseUrl = remoteConfig["BASE_URL"]
                AudioUrl.saveBaseUrl(baseUrl.asString())
            }
        }
    }

}