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
        get() = R.color.white
    override val isLightStatusBar: Boolean
        get() = true
    override val navigationBarColor: Int
        get() = R.color.cl_color_primary_dark
    override val navigationBarColorV28: Int
        get() = R.color.white
    override val isLightNavigationBar: Boolean
        get() = true
    override val backgroundColor: Int
        get() = R.color.cl_background
    override val actionBarBackColor: Int
        get() = R.color.cl_background_light
    override val specialBackColor: Int
        get() = R.color.cl_background_light
    override val menuBackColor: Int
        get() = backgroundColor
    override val contentBackColor: Int
        get() = R.color.cl_content_back
    override val checkedBackColor: Int
        get() = R.color.cl_selected_back
    override val breakLineColor: Int
        get() = R.color.cl_break_line
    override val defaultTextColor: Int
        get() = R.color.cl_def_text_color
    override val actionBarTextColor: Int
        get() = R.color.cl_def_text_color
    override val secondaryTextColor: Int
        get() = R.color.cl_secondary_text_color
    override val specialTextColor: Int
        get() = R.color.cl_separate_text_color
    override val checkedTextColor: Int
        get() = R.color.cl_selected_text

    override val contentTextColor: Int
        get() = R.color.cl_content_text
    override val edtBackColor: Int
        get() = R.color.cl_edt_back
}
