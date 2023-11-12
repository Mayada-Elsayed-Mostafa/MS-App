package com.example.msapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        drawerLayout = findViewById(R.id.drawer_layout)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav)

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setupWithNavController(navController)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> replaceFragment(HomeFragment())
                R.id.navigation_dashboard -> replaceFragment(DashboardFragment())
                R.id.navigation_community -> {
                    val communityIntent = Intent(this, CommunityActivity::class.java)
                    startActivity(communityIntent)
                }
                R.id.navigation_notes -> {
                    val notesIntent = Intent(this, NotesActivity::class.java)
                    startActivity(notesIntent)
                }
            }
            true
        }

        // Set the default fragment when the activity is created
        if (savedInstanceState == null) {
            bottomNavigationView.selectedItemId = R.id.navigation_home
        }

        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
            navigationView.setCheckedItem(R.id.navigation_home)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_home -> replaceFragment(HomeFragment())
            R.id.navigation_calendar-> replaceFragment(CalendarFragment())
            R.id.nav_settings -> {
                val settingsIntent = Intent(this, SettingsActivity::class.java)
                startActivity(settingsIntent)
            }
            R.id.nav_share -> replaceFragment(ShareFragment())
            R.id.nav_about -> replaceFragment(FaqFragment())
            R.id.nav_notes -> {
                val notesIntent = Intent(this, NotesActivity::class.java)
                startActivity(notesIntent)
            }
            R.id.nav_logout -> handleLogout()
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
    private fun handleLogout() {
        // Update user's state in SharedPreferences
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putBoolean("isLoggedIn", false)
            apply()
        }

        // Navigate to the login screen or perform any other necessary actions
        val loginIntent = Intent(this, LastOnBoardingActivity::class.java)
        startActivity(loginIntent)
        finish()
    }

}
