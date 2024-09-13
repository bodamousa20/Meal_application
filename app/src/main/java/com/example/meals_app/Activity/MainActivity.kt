package com.example.meals_app.Activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.meals_app.Activity.Fragment.HomeFragment
import com.example.meals_app.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val bottonNavigation = findViewById<BottomNavigationView>(R.id.btn_nav)
        val controller = Navigation.findNavController(this@MainActivity, R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(bottonNavigation,controller)



    }
}