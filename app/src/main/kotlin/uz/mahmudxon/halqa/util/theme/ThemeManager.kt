package uz.mahmudxon.halqa.util.theme

import uz.mahmudxon.halqa.util.Prefs
import javax.inject.Inject

class ThemeManager @Inject constructor(
    private val prefs: Prefs,
    val themes: Set<@JvmSuppressWildcards Theme>
) {
    var currentTheme: Theme
        get() {
            val id = prefs.get(prefs.theme, Theme.CLASSIC)
            themes.forEach {
                if (id == it.id)
                    return it
            }
            return themes.first()
        }
        set(value) {
            prefs.save(prefs.theme, value.id)
            if (value.isDark)
                prefs.save(prefs.lastDarkTheme, value.id)
            else prefs.save(prefs.lastLightTheme, value.id)
        }

    var lastDarkTheme: Theme
        get() {
            val id = prefs.get(prefs.lastDarkTheme, Theme.NIGHT_MODE)
            themes.forEach {
                if (id == it.id && it.isDark)
                    return it
            }
            return themes.first()
        }
        set(value) {
            prefs.save(prefs.lastDarkTheme, value.id)
        }

    var lastLightTheme: Theme
        get() {
            val id = prefs.get(prefs.lastLightTheme, Theme.CLASSIC)
            themes.forEach {
                if (id == it.id && !it.isDark)
                    return it
            }
            return themes.first()
        }
        set(value) {
            prefs.save(prefs.lastLightTheme, value.id)
        }
}
