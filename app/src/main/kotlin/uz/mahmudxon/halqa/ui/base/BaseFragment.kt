package uz.mahmudxon.halqa.ui.base

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.LayoutRes
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import uz.mahmudxon.halqa.util.theme.IThemeChanger
import uz.mahmudxon.halqa.util.theme.Theme
import uz.mahmudxon.halqa.util.theme.ThemeManager
import javax.inject.Inject

abstract class BaseFragment<T : ViewDataBinding>(@LayoutRes private val layout: Int) : Fragment() {

    private var isUseBackPress = true

    @Inject
    lateinit var themeManager: ThemeManager

    protected val navController: NavController by lazy { findNavController() }


    protected val themeChanger: IThemeChanger? by lazy {
        if (activity is IThemeChanger) activity as IThemeChanger
        else null
    }

    protected lateinit var binding: T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layout, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.isFocusableInTouchMode = true
        view.requestFocus()
        view.setOnKeyListener { _, keyCode, e ->
            if (keyCode == KeyEvent.KEYCODE_BACK && e.action == KeyEvent.ACTION_DOWN) {
                isUseBackPress = true
                onBackPressed()
                return@setOnKeyListener isUseBackPress
            } else return@setOnKeyListener false
        }
        onCreate(view)
        notifyThemeChanged()
    }

    abstract fun onCreate(view: View)

    open fun onBackPressed() {
        isUseBackPress = false
    }


    protected fun notifyThemeChanged() = onCreateTheme(themeManager.currentTheme)

    open fun onCreateTheme(theme: Theme) {
        context?.let { ctx ->
            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                    val statusBarColor = ContextCompat.getColor(ctx, theme.statusBarColorV23)
                    val navBarColor = ContextCompat.getColor(ctx, theme.navigationBarColorV28)
                    setStatusBar(statusBarColor, theme.isLightStatusBar)
                    setNavBarColor(navBarColor, theme.isLightNavigationBar)
                }
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                    val statusBarColor = ContextCompat.getColor(ctx, theme.statusBarColorV23)
                    val navBarColor = ContextCompat.getColor(ctx, theme.navigationBarColor)
                    setStatusBar(statusBarColor, theme.isLightStatusBar)
                    setNavBarColor(navBarColor)
                }
                else -> {
                    val statusBarColor = ContextCompat.getColor(ctx, theme.statusBarColor)
                    val navBarColor = ContextCompat.getColor(ctx, theme.navigationBarColor)
                    setStatusBar(statusBarColor)
                    setNavBarColor(navBarColor)
                }
            }
        }
    }

    private fun setStatusBar(statusBarColor: Int) {
        activity?.window?.statusBarColor = statusBarColor
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setStatusBar(statusBarColor: Int, isLight: Boolean) {
        activity?.window?.statusBarColor = statusBarColor
        if (isLight)
            activity?.window?.decorView?.let { view ->
                view.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        else
            activity?.window?.decorView?.let { view ->
                view.systemUiVisibility = 0
            }
    }

    private fun setNavBarColor(navBarColor: Int) {
        activity?.window?.navigationBarColor = navBarColor
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setNavBarColor(navBarColor: Int, isLight: Boolean) {
        activity?.window?.navigationBarColor = navBarColor
        if (isLight)
            activity?.window?.decorView?.let { view ->
                view.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        else activity?.window?.decorView?.let { view ->
            view.systemUiVisibility = 0
        }
    }


    fun hideKeyBoard() {
        val view = activity?.currentFocus ?: View(activity)
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun showKeyboard(editText: EditText) {
        editText.requestFocus()
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, 0)
    }

    fun finish()
    {
        navController.popBackStack()
    }
}
