package uz.mahmudxon.halqa.datasource.network

import android.app.Application
import java.io.File

object AudioUrl {

    private lateinit var _filesDir: String
    fun generate(chapterId: Int): String? {
        if (chapterId < 1 || chapterId > 32)
            return null
        return "http://mahmudxon.uz/halqa/$chapterId-bob.m4a"
    }

    fun offLineUrl(chapterId: Int) = "$_filesDir${chapterId}.m4a"

    fun init(app: Application) {
        _filesDir = app.filesDir.absolutePath + "/files/"
        val dir = File(_filesDir)
        if (!dir.exists())
            dir.mkdirs()
    }
}