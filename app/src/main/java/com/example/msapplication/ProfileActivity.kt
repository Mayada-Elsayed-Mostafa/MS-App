package com.example.msapplication

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val currentUser = auth.currentUser
        val userName = findViewById<TextView>(R.id.name_tv)
        val email = findViewById<TextView>(R.id.email_tv)

        if (currentUser != null) {
            val userId = currentUser.uid

            db.collection("users")
                .document(userId)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        userName.text = document.getString("username")
                        email.text = document.getString("email")
                    } else {
                        Log.d(TAG, "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting document", exception)
                }
        }

        val documentsCard = findViewById<LinearLayout>(R.id.documents_card)
        documentsCard.setOnClickListener {
            val intentToDocumentsPage = Intent(this, DocumentsActivity::class.java)
            startActivity(intentToDocumentsPage)
        }

    }
}