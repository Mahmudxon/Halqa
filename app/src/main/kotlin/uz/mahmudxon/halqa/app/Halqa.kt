package uz.mahmudxon.halqa.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import uz.mahmudxon.halqa.datasource.network.AudioUrl

@HiltAndroidApp
class Halqa : Application() {
    override fun onCreate() {
        super.onCreate()
        AudioUrl.init(this)
    }
}