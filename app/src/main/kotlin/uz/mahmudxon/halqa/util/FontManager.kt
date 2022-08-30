package uz.mahmudxon.halqa.util

import javax.inject.Inject

class FontManager @Inject constructor(private val prefs: Prefs) {
    var fontSize:Int
        get() = prefs.get(prefs.fontSize, 18)
        set(value) {
            prefs.save(prefs.fontSize, value)
        }
}