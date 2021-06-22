package com.slv.mysubmissionandroidexpert.presentation.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.slv.mysubmissionandroidexpert.databinding.ActivitySplashscreenBinding
import com.slv.mysubmissionandroidexpert.presentation.MainActivity

class SplashscreenActivity : AppCompatActivity() {

    private lateinit var activitySplashscreenBinding: ActivitySplashscreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        activitySplashscreenBinding = ActivitySplashscreenBinding.inflate(layoutInflater)
        setContentView(activitySplashscreenBinding.root)

        val delay: Long = 2000
        Handler(mainLooper).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, delay)
    }
}