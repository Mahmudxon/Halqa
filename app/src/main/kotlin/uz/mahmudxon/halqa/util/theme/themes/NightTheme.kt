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
        get() = R.color.n_color_primary_dark
    override val isLightStatusBar: Boolean
        get() = false
    override val navigationBarColor: Int
        get() = R.color.n_color_primary_dark
    override val navigationBarColorV28: Int
        get() = R.color.n_color_primary_dark
    override val isLightNavigationBar: Boolean
        get() = false
    override val backgroundColor: Int
        get() = R.color.n_background
    override val actionBarBackColor: Int
        get() = R.color.n_background_light
    override val specialBackColor: Int
        get() = R.color.n_background_light
    override val menuBackColor: Int
        get() = specialBackColor
    override val contentBackColor: Int
        get() = backgroundColor
    override val breakLineColor: Int
        get() = R.color.n_break_line
    override val defaultTextColor: Int
        get() = R.color.n_def_text_color
    override val actionBarTextColor: Int
        get() = R.color.n_def_text_color
    override val secondaryTextColor: Int
        get() = R.color.n_secondary_text_color
    override val specialTextColor: Int
        get() = R.color.n_separate_text_color

    override val checkedBackColor: Int
        get() = R.color.n_selected_back
    override val checkedTextColor: Int
        get() = R.color.n_selected_text
    override val contentTextColor: Int
        get() = R.color.n_content_text
    override val edtBackColor: Int
        get() = R.color.n_edt_back
}
