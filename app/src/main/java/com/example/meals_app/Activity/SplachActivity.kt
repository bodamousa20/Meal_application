package com.example.meals_app.Activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.meals_app.Activity.LoginActivity.LoginActivity
import com.example.meals_app.R

class SplachActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splach)
        Handler(Looper.getMainLooper()).postDelayed({
            // Navigate to MainActivity after the delay
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()  // Close the SplashActivity
        }, 2000)  // 3000 ms = 3 seconds delay
    }
    }
