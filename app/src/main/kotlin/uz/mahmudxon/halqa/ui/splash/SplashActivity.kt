package uz.mahmudxon.halqa.ui.splash

import android.annotation.SuppressLint
import android.content.ClipData.newIntent
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.WindowManager
import dagger.hilt.android.AndroidEntryPoint
import uz.mahmudxon.halqa.R
import uz.mahmudxon.halqa.ui.MainActivity
//
//@SuppressLint("CustomSplashScreen")
//@AndroidEntryPoint
//class SplashActivity : AppCompatActivity() {
//
//    private val countDownTimer = object : CountDownTimer(2000, 1000) {
//        override fun onFinish() {
//            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
//            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
//            finish()
//        }
//
//        override fun onTick(millisUntilFinished: Long) {
//        }
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_splash)
//    }
//
//    override fun onStart() {
//        super.onStart()
//        countDownTimer.start()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        countDownTimer.cancel()
//    }
//}