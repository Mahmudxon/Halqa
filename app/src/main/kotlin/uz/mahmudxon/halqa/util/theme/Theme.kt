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

    /** Background **/
    abstract val backgroundColor: Int
    abstract val actionBarBackColor: Int
    abstract val specialBackColor: Int
    abstract val breakLineColor: Int
    abstract val menuBackColor: Int
    abstract val contentBackColor: Int
    abstract val checkedBackColor: Int
    abstract val edtBackColor: Int
    /** End Background **/

    /** Text & Icon **/
    abstract val defaultTextColor: Int
    abstract val actionBarTextColor: Int
    abstract val secondaryTextColor: Int
    abstract val specialTextColor: Int
    abstract val checkedTextColor: Int
    abstract val contentTextColor: Int

    val transparent: Int = R.color.transparent

    /** End Text & Icon **/

    companion object {
        const val CLASSIC = 0
        const val NIGHT_MODE = 1
    }
}
