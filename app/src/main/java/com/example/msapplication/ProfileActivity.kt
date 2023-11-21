package com.example.msapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser
        val userName = findViewById<TextView>(R.id.name_tv)
        val email = findViewById<TextView>(R.id.email_tv)

        if (currentUser != null) {
            val userId = currentUser.uid

            db.collection("users")
                .document(userId)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        val username = document.getString("username")
                        val userEmail = document.getString("email")

                        Log.d("ProfileActivity", "Document data: $document")

                        if (username != null) {
                            userName.text = username
                            Log.d("ProfileActivity", "Username: $username")
                        } else {
                            Log.d("ProfileActivity", "Username is null")
                        }

                        if (userEmail != null) {
                            email.text = userEmail
                            Log.d("ProfileActivity", "Email: $userEmail")
                        } else {
                            Log.d("ProfileActivity", "Email is null")
                        }
                    } else {
                        Log.d("ProfileActivity", "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("ProfileActivity", "Error getting document", exception)
                }
        }

        val documentsCard = findViewById<LinearLayout>(R.id.documents_card)
        documentsCard.setOnClickListener {
            val intentToDocumentsPage = Intent(this, DocumentsActivity::class.java)
            startActivity(intentToDocumentsPage)
        }

    }
}