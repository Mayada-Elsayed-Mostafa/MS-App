package com.example.msapplication.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat.startActivity
import com.example.msapplication.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private val sharedPreferences by lazy {
        getSharedPreferences("MyPrefs", MODE_PRIVATE)
    }

    companion object {
        const val REQUEST_CODE_HOME = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Reference the Toolbar from the layout
        val toolbar = findViewById<Toolbar>(R.id.signIn_toolbar!!)
        toolbar.title = ""

        // Set the Toolbar as the support action bar
        setSupportActionBar(toolbar)

        mAuth = FirebaseAuth.getInstance()

        val loginBtn = findViewById<Button>(R.id.login_btn)
        val emailEd = findViewById<TextInputEditText>(R.id.email)
        val passwordEd = findViewById<TextInputEditText>(R.id.password)

        val signUpTv = findViewById<TextView>(R.id.sign_in_tv)
        signUpTv.setOnClickListener {
            val intentToSignupActivity = Intent(this, SignupActivity::class.java)
            startActivity(intentToSignupActivity)
        }

        // Check the login state when the LoginActivity is opened
        if (isLoggedIn()) {
            navigateToHome(null)
        }

        loginBtn.setOnClickListener {
            val email = emailEd.text.toString()
            val password = passwordEd.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Save login state
                        saveLoginState(true)

                        // Retrieve UID of the logged-in user
                        val uid = mAuth.currentUser?.uid

                        // Pass UID to home activity
                        navigateToHome(uid)
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Authentication failed.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            } else {
                Toast.makeText(
                    applicationContext,
                    "Enter your Email and Password to continue",
                    Toast.LENGTH_LONG
                ).show()
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

    private fun navigateToHome(uid: String?) {
        val intentToHomeActivity = Intent(this, HomeActivity::class.java)
        // Pass UID to HomeActivity
        intentToHomeActivity.putExtra("UID", uid)
        startActivity(this, intentToHomeActivity, null)
        finish()
    }

    // Handle the result when returning from HomeActivity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_HOME && resultCode == RESULT_OK) {
            // Handle any logic if needed after returning from HomeActivity
        }
    }

}