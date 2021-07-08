package com.internshala.activitylifecycle.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.internshala.activitylifecycle.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            val switchScreen = Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(switchScreen)
            this.finish()
        }, 1000)
    }
}