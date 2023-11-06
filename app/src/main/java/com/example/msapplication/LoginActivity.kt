package com.example.msapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private val sharedPreferences by lazy {
        getSharedPreferences("MyPrefs", MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        val loginBtn = findViewById<Button>(R.id.login_btn)
        val emailEd = findViewById<TextInputEditText>(R.id.email)
        val passwordEd = findViewById<TextInputEditText>(R.id.password)

        val signUpTv = findViewById<TextView>(R.id.sign_in_tv!!)
        signUpTv.setOnClickListener {
            val intentToSignupActivity = Intent(this, SignupActivity::class.java)
            startActivity(intentToSignupActivity)
        }

        // Check the login state when the LoginActivity is opened
        if (isLoggedIn()) {
            navigateToHome()
        }

        loginBtn.setOnClickListener {
            val email = emailEd.text.toString()
            val password = passwordEd.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        // Save login state
                        saveLoginState(true)
                        navigateToHome()
                    }
                }
            } else {
                Toast.makeText(applicationContext, "Enter your Email and Password to continue", Toast.LENGTH_LONG).show()
            }
        }
    }

    // Function to save login state
    private fun saveLoginState(isLoggedIn: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", isLoggedIn)
        editor.apply()
    }

    // Function to check login state
    private fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }

    private fun navigateToHome() {
        val intentToHomeActivity = Intent(this, HomeActivity::class.java)
        startActivity(intentToHomeActivity)
        finish()
    }
}
