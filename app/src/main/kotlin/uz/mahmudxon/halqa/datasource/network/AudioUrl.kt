package uz.mahmudxon.halqa.datasource.network

import android.app.Application
import uz.mahmudxon.halqa.util.Prefs
import java.io.File

object AudioUrl {

    private lateinit var _filesDir: String
    private lateinit var prefs: Prefs
    val baseUrl
        get() = prefs.get(prefs.baseUrl, "http://mahmudxon.uz/halqa/")

    fun generate(chapterId: Int): String? {
        if (chapterId < 1 || chapterId > 32)
            return null
        return "$baseUrl$chapterId-bob.m4a"
    }

    fun offLineUrl(chapterId: Int) = "$_filesDir${chapterId}.m4a"

    fun init(app: Application) {
        _filesDir = app.filesDir.absolutePath + "/files/"
        prefs = Prefs(app)
        val dir = File(_filesDir)
        if (!dir.exists())
            dir.mkdirs()
    }

    fun saveBaseUrl(baseUrl: String) {
        if (this.baseUrl != baseUrl) {
            prefs.save(prefs.baseUrl, baseUrl)
        }
    }
}