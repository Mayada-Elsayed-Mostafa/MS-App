package com.example.msapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LastOnBoardingActivity : AppCompatActivity() {

    private lateinit var signUpBtn: Button
    private lateinit var logInBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_last_on_boarding)


        signUpBtn = findViewById(R.id.signUp_btn)

        logInBtn = findViewById(R.id.logIn_btn)

        signUpBtn.setOnClickListener {
            val intentToSignUp = Intent(this, SignupActivity::class.java)
            startActivity(intentToSignUp)
        }

        logInBtn.setOnClickListener {
            val intentToLogin = Intent(this, LoginActivity::class.java)
            startActivity(intentToLogin)
        }
    }
}