package uz.mahmudxon.halqa.ui

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import uz.mahmudxon.halqa.databinding.ActivityMainBinding
import uz.mahmudxon.halqa.util.theme.IThemeChanger
import uz.mahmudxon.halqa.util.theme.ThemeManager
import javax.inject.Inject
import kotlin.math.hypot

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), IThemeChanger {

    private var canChangeTheme = true
    private var finalRadius: Float = 0F

    @Inject
    lateinit var themeManager: ThemeManager

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
    }


    override fun takeScreenshot() {
        canChangeTheme = false
        val container = binding.root
        val w = container.measuredWidth
        val h = container.measuredHeight
        finalRadius = hypot(w.toFloat(), h.toFloat())
        val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        container.draw(canvas)
        binding.screenShoot.setImageBitmap(bitmap)
        binding.screenShoot.visibility = View.VISIBLE
        binding.navHostFragment.visibility = View.INVISIBLE
    }

    override fun startCircularAnimation(x: Int, y: Int, isReverse: Boolean) {
        binding.navHostFragment.visibility = View.VISIBLE
        val start = if (isReverse) finalRadius else 0f
        val finally = if (isReverse) 0f else finalRadius
        binding.screenShoot.elevation = if (isReverse) 30f else 0f
        val anim = ViewAnimationUtils.createCircularReveal(
            if (isReverse) binding.screenShoot else binding.navHostFragment,
            x,
            y,
            start,
            finally
        )
        anim.duration = 500L
        anim.doOnEnd {
            canChangeTheme = true
            binding.screenShoot.visibility = View.GONE
        }
        anim.start()
    }

    override fun canChangeTheme() = canChangeTheme

}