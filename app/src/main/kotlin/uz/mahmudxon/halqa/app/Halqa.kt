package uz.mahmudxon.halqa.app

import android.app.Application
import com.downloader.PRDownloader
import com.downloader.PRDownloaderConfig
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class Halqa : Application() {
    override fun onCreate() {
        super.onCreate()
        val config = PRDownloaderConfig.newBuilder()
            .setDatabaseEnabled(true)
            .build()
        PRDownloader.initialize(applicationContext, config)
    }
}