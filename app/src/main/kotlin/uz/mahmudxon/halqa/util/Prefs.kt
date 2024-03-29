package uz.mahmudxon.halqa.util

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Prefs @Inject constructor(@ApplicationContext context: Context) {
    val baseUrl = "baseUrl"
    val theme = "currentTheme"
    val lastDarkTheme = "lastDarkTheme"
    val lastLightTheme = "lastLightTheme"
    val autoDarkTheme = "autoDarkTheme"
    val fontSize = "fontSize"
    val audioItemDownloaded = "audioBook"
    val downloadSize  = "downloadSize"
    val iKnowAudioSizes = "knowAudioSizes"

    private val prefsName: String = "Mahmudxon@${context.applicationInfo.packageName}"
    private val prefs: SharedPreferences by lazy {
        context.getSharedPreferences(
            prefsName,
            Context.MODE_PRIVATE
        )
    }

    fun save(key: String, value: Int) {
        prefs.edit().putInt(key, value).apply()
    }

    fun save(key: String, value: String?) {
        prefs.edit().putString(key, value).apply()
    }

    fun save(key: String, value: Float) {
        prefs.edit().putFloat(key, value).apply()
    }

    fun save(key: String, value: Boolean?) {
        prefs.edit().putBoolean(key, value ?: false).apply()
    }

    fun save(key: String, value: Long) {
        prefs.edit().putLong(key, value).apply()
    }

    fun get(key: String, defValue: Int) = prefs.getInt(key, defValue)

    fun get(key: String, defValue: String) = prefs.getString(key, defValue) ?: ""

    fun get(key: String, defValue: Float) = prefs.getFloat(key, defValue)

    fun get(key: String, defValue: Boolean) = prefs.getBoolean(key, defValue)

    fun get(key: String, defValue: Long) = prefs.getLong(key, defValue)

    fun clear() {
        prefs.edit().clear().apply()
    }
}
