package uz.mahmudxon.halqa.util.theme.themes

import uz.mahmudxon.halqa.R
import uz.mahmudxon.halqa.util.theme.Theme
import javax.inject.Inject

class NightTheme @Inject constructor() : Theme() {
    override val id: Int
        get() = NIGHT_MODE
    override val name: Int
        get() = R.string.night_mode
    override val style: Int
        get() = R.style.NightTheme
    override val isDark: Boolean
        get() = true
    override val statusBarColor: Int
        get() = R.color.n_color_primary_dark
    override val statusBarColorV23: Int
        get() = R.color.n_action_bar_color
    override val isLightStatusBar: Boolean
        get() = false
    override val navigationBarColor: Int
        get() = R.color.n_color_primary_dark
    override val navigationBarColorV28: Int
        get() = R.color.n_action_bar_color
    override val isLightNavigationBar: Boolean
        get() = false

    override val backgroundColor: Int
        get() = R.color.n_background
    override val cardColor: Int
        get() = R.color.n_card
    override val primaryTextColor: Int
        get() = R.color.n_primary_text
    override val secondaryTextColor: Int
        get() = R.color.n_secondary_text
    override val actionBarColor: Int
        get() = R.color.n_action_bar_color
    override val breakLineColor: Int
        get() = R.color.n_break_line
}
