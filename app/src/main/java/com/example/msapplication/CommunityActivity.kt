package com.example.msapplication

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView

class CommunityActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community)

        val toolbarCommunity = findViewById<Toolbar>(R.id.toolbar_community)
        setSupportActionBar(toolbarCommunity)

        toolbarCommunity.title = "Community Forum"
        // Add icons to the toolbar
        toolbarCommunity.inflateMenu(R.menu.menu_toolbar_community)
        toolbarCommunity.setOnMenuItemClickListener { item -> onMenuItemClick(item) }

        val startPost = findViewById<CardView>(R.id.start_post)
        startPost.setOnClickListener {
            // Show the custom dialog for creating a post
            showCreatePostDialog()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_community, menu)
        return true
    }

    private fun onMenuItemClick(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_profile -> {
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.menu_notifications -> {
                val intent = Intent(this, NotificationActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.menu_chats -> {
                val intent = Intent(this, ChatsActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun showCreatePostDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_create_post, null)

        AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Create a Post")
            .setPositiveButton("Post") { dialog, which ->
                // Handle the post button click (e.g., post the content to a server)
                Toast.makeText(this, "Post button clicked!", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel") { dialog, which ->
                // Handle the cancel button click
                dialog.dismiss()
            }
            .show()
    }
}