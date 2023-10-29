package com.kkosunae.view.activity

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.activity.viewModels
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Observer
import com.kkosunae.R
import com.kkosunae.viewmodel.MainViewModel
import kotlinx.coroutines.delay
import kotlin.concurrent.thread

class SplashActivity : AppCompatActivity() {

    private lateinit var splashScreen: SplashScreen
    val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashScreen = installSplashScreen()

        startSplash()

        setContentView(R.layout.activity_splash)
        initObserver()


    }

    private fun startSplash() {

        splashScreen.setOnExitAnimationListener { splashScreenView ->
            val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1f, 5f, 1f)
            val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f, 5f, 1f)

            ObjectAnimator.ofPropertyValuesHolder(splashScreenView.iconView, scaleX, scaleY).run {
                interpolator = AnticipateInterpolator()
                duration = 1000L
                doOnEnd {
                    splashScreenView.remove()
                    mainViewModel.setIsLogin(true)
                }
                start()
            }
        }
    }

    private fun initObserver() {
        mainViewModel.getIsLogin().observe(this, Observer { it ->
            if (it) {
                val intent = Intent(this, MainActivity::class.java)
                // 액티비티 스택 제거
                finishAffinity()
                startActivity(intent)
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                // 액티비티 스택 제거
                finishAffinity()
                startActivity(intent)
            }
        })
    }
}