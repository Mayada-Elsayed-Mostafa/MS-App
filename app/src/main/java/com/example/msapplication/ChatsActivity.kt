package com.example.msapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class ChatsActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPagerAdapter: ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chats)

        tabLayout = findViewById(R.id.chats_tabs)
        viewPagerAdapter = findViewById(R.id.chats_viewPager)

        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.direct_messages).toString()))
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.groups).toString()))

        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = ChatsAdapter(this, supportFragmentManager, tabLayout.tabCount )
        viewPagerAdapter.adapter = adapter

        viewPagerAdapter.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPagerAdapter.currentItem = tab!!.position

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })
    }
}