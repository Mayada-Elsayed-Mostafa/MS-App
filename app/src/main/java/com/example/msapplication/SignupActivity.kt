package com.example.msapplication

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        auth = FirebaseAuth.getInstance()

        val emailEd = findViewById<TextInputEditText>(R.id.email)
        val passwordEd = findViewById<TextInputEditText>(R.id.password)
        val signUpBtn = findViewById<Button>(R.id.signUp_btn)
        val progressSignUp = findViewById<ProgressBar>(R.id.progress_bar_signUp)

        signUpBtn.setOnClickListener {
            val email = emailEd.text.toString()
            val password = passwordEd.text.toString()

            if(email.isNotEmpty() && password.isNotEmpty()){
                progressSignUp.visibility = View.VISIBLE
                //progressSignUp.setBackgroundColor(Color.WHITE)

                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if(it.isSuccessful){
                        Toast.makeText(applicationContext, "Successfully", Toast.LENGTH_LONG).show()
                        progressSignUp.visibility = View.GONE
                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                    } else{
                        Toast.makeText(applicationContext, it.exception.toString(), Toast.LENGTH_LONG).show()
                    }
                }
            } else{
                Toast.makeText(applicationContext, "Enter your Email and Password to continue", Toast.LENGTH_LONG).show()
            }
        }

    }
}