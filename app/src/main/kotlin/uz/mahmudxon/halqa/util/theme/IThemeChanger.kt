package uz.mahmudxon.halqa.util.theme

interface IThemeChanger {
    fun takeScreenshot()
    fun startCircularAnimation(x: Int, y: Int, isReverse : Boolean)
    fun canChangeTheme(): Boolean
}