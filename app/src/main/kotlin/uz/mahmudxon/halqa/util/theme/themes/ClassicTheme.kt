package uz.mahmudxon.halqa.util.theme.themes

import uz.mahmudxon.halqa.R
import uz.mahmudxon.halqa.util.theme.Theme
import javax.inject.Inject

class ClassicTheme @Inject constructor() : Theme() {
    override val id: Int
        get() = CLASSIC
    override val name: Int
        get() = R.string.classic
    override val style: Int
        get() = R.style.Classic
    override val isDark: Boolean
        get() = false
    override val statusBarColor: Int
        get() = R.color.cl_color_primary_dark
    override val statusBarColorV23: Int
        get() = R.color.cl_action_bar_color
    override val isLightStatusBar: Boolean
        get() = true
    override val navigationBarColor: Int
        get() = R.color.cl_color_primary_dark
    override val navigationBarColorV28: Int
        get() = R.color.cl_action_bar_color
    override val isLightNavigationBar: Boolean
        get() = true
    override val backgroundColor: Int
        get() = R.color.cl_background
    override val cardColor: Int
        get() = R.color.cl_card
    override val primaryTextColor: Int
        get() = R.color.cl_primary_text
    override val secondaryTextColor: Int
        get() = R.color.cl_secondary_text
    override val actionBarColor: Int
        get() = R.color.cl_action_bar_color
    override val breakLineColor: Int
        get() = R.color.cl_break_line
}
