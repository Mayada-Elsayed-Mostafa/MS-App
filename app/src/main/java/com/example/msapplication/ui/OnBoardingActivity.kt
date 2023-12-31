package com.example.msapplication.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.msapplication.OnBoardingAdapter
import com.example.msapplication.R
import com.example.msapplication.data.domain.Page
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var onBoardingViewPager2: ViewPager2
    lateinit var nextBtn: Button
    lateinit var skipBtn: Button
    lateinit var previousBtn: Button
    private lateinit var onBoardingPageChangeCallback: ViewPager2.OnPageChangeCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)

        val welcome = getString(R.string.welcome_to_our_app).toString()
        val track = getString(R.string.track_your_symptoms).toString()
        val connect = getString(R.string.connect_with_others).toString()
        val letsGetStarted = getString(R.string.lets_get_started).toString()
        val lets = getString(R.string.lets).toString()
        val begin = getString(R.string.begin).toString()
        val join = getString(R.string.join).toString()
        val use_our_app = getString(R.string.use_our_app).toString()

        val pageList = arrayListOf(
            Page(
                welcome,
                R.drawable.img_welcoming,
                letsGetStarted
            ),

            Page(
                track,
                R.drawable.img_doctor,
                use_our_app
            ),

            Page(
                connect,
                R.drawable.img_connection,
                join
            ),

            Page(
                lets,
                R.drawable.img_patient,
                begin
            )
        )

        onBoardingViewPager2 = findViewById(R.id.onboardingViewPager2)
        nextBtn = findViewById(R.id.next_btn)
        skipBtn = findViewById(R.id.skip_btn)
        previousBtn = findViewById(R.id.previous_btn)

        val onBoardingPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                when (position) {
                    0 -> {
                        skipBtn.text = getString(R.string.skip)
                        skipBtn.visibility = android.view.View.VISIBLE
                        nextBtn.visibility = android.view.View.VISIBLE
                        previousBtn.visibility = android.view.View.GONE
                    }

                    3 -> {
                        nextBtn.text = getString(R.string.get_started)
                        skipBtn.visibility = android.view.View.GONE
                        nextBtn.visibility = android.view.View.VISIBLE
                        previousBtn.visibility = android.view.View.VISIBLE
                    }

                    else -> {
                        skipBtn.text = getString(R.string.skip)
                        skipBtn.visibility = android.view.View.VISIBLE
                        nextBtn.visibility = android.view.View.VISIBLE
                        previousBtn.visibility = android.view.View.VISIBLE
                    }
                }
            }
        }

        onBoardingViewPager2.apply {
            adapter = OnBoardingAdapter(this@OnBoardingActivity, pageList)
            registerOnPageChangeCallback(onBoardingPageChangeCallback)
            (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        TabLayoutMediator(tabLayout, onBoardingViewPager2) { tab, position -> }.attach()

        nextBtn.setOnClickListener {
            if (onBoardingViewPager2.currentItem < onBoardingViewPager2.adapter!!.itemCount - 1) {
                onBoardingViewPager2.currentItem += 1
            } else {
                onboardingCompleted()
                homeScreenIntent()
            }
        }

        skipBtn.setOnClickListener {
            homeScreenIntent()
        }

        previousBtn.setOnClickListener {
            if (onBoardingViewPager2.currentItem > 0) {
                onBoardingViewPager2.currentItem -= 1
            }
        }
    }

    private fun onboardingCompleted() {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("onboarding_completed", true)
        editor.apply()
    }

    override fun onDestroy() {
        onBoardingViewPager2.unregisterOnPageChangeCallback(onBoardingPageChangeCallback)
        super.onDestroy()
    }

    private fun homeScreenIntent() {
        val homeIntent = Intent(this, LastOnBoardingActivity::class.java)
        startActivity(homeIntent)
    }
}
