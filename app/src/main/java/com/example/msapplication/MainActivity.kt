package com.example.msapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(1000)
        installSplashScreen()
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        // Check if onboarding is completed
        val onboardingCompleted = sharedPreferences.getBoolean("onboarding_completed", false)

        if (onboardingCompleted) {
            // Onboarding is completed, now check login state
            checkLoginState()
        } else {
            // Onboarding is not completed, show the onboarding screens
            showOnboarding()
        }
    }

    private fun showOnboarding() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, OnBoardingActivity::class.java))
            finish()
        }, 2000)
    }

    private fun checkLoginState() {
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

        if (isLoggedIn) {
            // User is logged in, navigate to the main part of your app
            navigateToMain()
        } else {
            // User is not logged in, show the login or signup screens
            // Replace the following line with your login or signup screen
            startActivity(Intent(this, LastOnBoardingActivity::class.java))
        }
    }

    private fun navigateToMain() {
        val mainIntent = Intent(this, HomeActivity::class.java)
        startActivity(mainIntent)
        finish()
    }
}
