package uz.mahmudxon.halqa.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Halqa : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}