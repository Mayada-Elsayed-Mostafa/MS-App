package com.example.msapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignupActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val sharedPreferences by lazy {
        getSharedPreferences("MyPrefs", MODE_PRIVATE)
    }
    var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val usernameEd = findViewById<TextInputEditText>(R.id.name)
        val emailEd = findViewById<TextInputEditText>(R.id.email)
        val passwordEd = findViewById<TextInputEditText>(R.id.password)
        val signUpBtn = findViewById<Button>(R.id.signUp_btn)
        val progressSignUp = findViewById<ProgressBar>(R.id.progress_bar_signUp)

        val signIn = findViewById<TextView>(R.id.sign_in_tv!!)
        signIn.setOnClickListener {
            val intentToLoginActivity = Intent(this, LoginActivity::class.java)
            startActivity(intentToLoginActivity)
        }

        // Check the login state when the SignupActivity is opened
        if (isLoggedIn()) {
            navigateToHome(null)
        }

        signUpBtn.setOnClickListener {
            val username = usernameEd.text.toString()
            val email = emailEd.text.toString()
            val password = passwordEd.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                progressSignUp.visibility = View.VISIBLE

                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Save login state
                        saveLoginState(true)

                        Toast.makeText(applicationContext, "Successfully", Toast.LENGTH_LONG).show()

                        // Retrieve UID of the newly created user
                        val user = auth.currentUser
                        val uid = user?.uid

                        if (uid != null) {
                            // Create a new user with a username and email
                            val userData = hashMapOf(
                                "username" to username,
                                "email" to email
                            )

                            // Add a new document with the UID as the document ID
                            db.collection("users")
                                .document(uid)
                                .set(userData)
                                .addOnSuccessListener {
                                    Log.d("SignupActivity", "DocumentSnapshot added with ID: $uid")
                                }
                                .addOnFailureListener { e ->
                                    Log.w("SignupActivity", "Error adding document", e)
                                }

                            progressSignUp.visibility = View.GONE
                            navigateToHome(uid)
                        } else {
                            // Handle the scenario where UID is unexpectedly null
                            Log.e("SignupActivity", "UID is unexpectedly null after successful authentication")
                        }
                    } else {
                        Toast.makeText(applicationContext, task.exception?.message ?: "Authentication failed", Toast.LENGTH_LONG).show()
                        progressSignUp.visibility = View.GONE
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

    private fun navigateToHome(uid: String?) {
        val intentToHomeActivity = Intent(this, HomeActivity::class.java)
        // Pass UID to HomeActivity if it's not null
        uid?.let {
            intentToHomeActivity.putExtra("UID", it)
        }
        startActivity(intentToHomeActivity)
        finish()
    }
}