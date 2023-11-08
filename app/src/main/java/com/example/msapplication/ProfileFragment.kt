package com.example.msapplication

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.reflect.Array.get

class ProfileFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    var db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        val userName = view.findViewById<TextView>(R.id.name_tv!!)
        val email = view.findViewById<TextView>(R.id.email_tv!!)

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

        val notificationCard = view.findViewById<LinearLayout>(R.id.notification_card)
        notificationCard.setOnClickListener{
            val intent = Intent(context, NotificationActivity::class.java)
            startActivity(intent)
        }


        val settingsCard = view.findViewById<CardView>(R.id.settings_card!!)
        settingsCard.setOnClickListener {
            val intentToSettingsPage = Intent(context, SettingsActivity::class.java)
            startActivity(intentToSettingsPage)
        }

        return view
    }
}