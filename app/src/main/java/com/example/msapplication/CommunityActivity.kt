package com.example.msapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar

class CommunityActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community)

        val toolbarCommunity = findViewById<Toolbar>(R.id.toolbar_community)
        setSupportActionBar(toolbarCommunity)

        // Add icons to the toolbar
        toolbarCommunity.inflateMenu(R.menu.menu_toolbar_community)
        toolbarCommunity.setOnMenuItemClickListener { item -> onMenuItemClick(item) }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_community, menu)
        return true
    }

    private fun onMenuItemClick(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_profile -> {
                // Handle profile icon click
                // Example: navigate to the profile screen
                // startActivity(ProfileActivity.newIntent(this))
                return true
            }
            R.id.menu_notifications -> {
                // Handle notifications icon click
                // Example: show notifications screen
                // startActivity(NotificationsActivity.newIntent(this))
                return true
            }
            R.id.menu_chats -> {
                // Handle chats icon click
                // Example: open chats activity
                // startActivity(ChatsActivity.newIntent(this))
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}