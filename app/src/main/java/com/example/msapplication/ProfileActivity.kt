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
        val userName = findViewById<TextView>(R.id.name_tv)
        val email = findViewById<TextView>(R.id.email_tv)

        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    userName.text = (document.getString("username"))
                    email.text = (document.getString("email"))

                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }

        val notificationCard = findViewById<LinearLayout>(R.id.notification_card)
        notificationCard.setOnClickListener{
            val intent = Intent(this, NotificationActivity::class.java)
            startActivity(intent)
        }


        val settingsCard = findViewById<LinearLayout>(R.id.settings_card)
        settingsCard.setOnClickListener {
            val intentToSettingsPage = Intent(this, SettingsActivity::class.java)
            startActivity(intentToSettingsPage)
        }

        val documentsCard = findViewById<LinearLayout>(R.id.documents_card)
        documentsCard.setOnClickListener {
            val intentToDocumentsPage = Intent(this, DocumentsActivity::class.java)
            startActivity(intentToDocumentsPage)
        }
    }
}