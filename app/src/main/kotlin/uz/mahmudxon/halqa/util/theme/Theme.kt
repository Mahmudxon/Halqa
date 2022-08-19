package uz.mahmudxon.halqa.util.theme

import uz.mahmudxon.halqa.R


abstract class Theme {
    abstract val id: Int
    abstract val name: Int
    abstract val style: Int
    abstract val isDark: Boolean

    /** System **/
    abstract val statusBarColor: Int
    abstract val statusBarColorV23: Int
    abstract val isLightStatusBar: Boolean
    abstract val navigationBarColor: Int
    abstract val navigationBarColorV28: Int
    abstract val isLightNavigationBar: Boolean
    /** End System **/

    abstract val backgroundColor: Int
    abstract val cardColor: Int
    abstract val primaryTextColor: Int
    abstract val secondaryTextColor: Int

    companion object {
        const val CLASSIC = 0
        const val NIGHT_MODE = 1
    }
}
