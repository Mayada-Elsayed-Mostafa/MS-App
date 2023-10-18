package com.example.msapplication

import android.content.Intent
import android.os.Build.VERSION_CODES.O
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewParent
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class OnBoardingActivity : AppCompatActivity() {

    private val OnBoardingPageChangeCallback = object: ViewPager2.OnPageChangeCallback(){
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)

            when (position) {
                0 -> {
                    skipBtn.text = "skip"
                    skipBtn.visibility = View.VISIBLE
                    nextBtn.visibility = View.VISIBLE
                    previousBtn.visibility = View.GONE
                }
                1 -> {
                    skipBtn.text = "Get Started"
                    skipBtn.visibility = View.GONE
                    nextBtn.visibility = View.VISIBLE
                    previousBtn.visibility = View.VISIBLE
                }
                else -> {
                    skipBtn.text = "skip"
                    skipBtn.visibility = View.VISIBLE
                    nextBtn.visibility = View.VISIBLE
                    previousBtn.visibility = View.VISIBLE
                }
            }
        }
    }

    private val pageList = arrayListOf(
        Page("Welcome to our app",
            R.drawable.logo,
            "Thank you for choosing our app to help you manage multiple sclerosis. Let's get started!",
            "#FFFFFF"),

        Page("What is MS?",
            R.drawable.img2,
            "Multiple sclerosis (MS) is a neurological condition that affects the central nervous system. It can cause a wide range of symptoms. Our app is here to help you understand and manage your MS.",
            "#FAF7E1"),
        Page("Track Your Symptoms",
            R.drawable.img3,
            "Use our app to log your MS symptoms, such as fatigue, pain, and mobility issues. This will help you and your healthcare provider better understand your condition.",
            "#FAF7E1"),

        Page("Connect with Others",
            R.drawable.img4,
            "Join our community of people living with MS. Share experiences, ask questions, and find support from others who understand what you're going through.",
            "#FAF7E1"),

        Page("Let's Get Started!",
            R.drawable.img5,
            "You're all set to begin your journey with our MS app. Tap \"Get Started\" to start tracking your symptoms and taking control of your MS.\n",
            "#FAF7E1")
    )

    lateinit var onBoardingViewPager2: ViewPager2
    lateinit var nextBtn: Button
    lateinit var skipBtn: Button
    lateinit var previousBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)

        onBoardingViewPager2 = findViewById(R.id.onboardingViewPager2)
        nextBtn = findViewById(R.id.next_btn)
        skipBtn = findViewById(R.id.skip_btn)
        previousBtn = findViewById(R.id.previous_btn)

        onBoardingViewPager2.apply {
            adapter = OnBoardingAdapter(this@OnBoardingActivity, pageList)
            registerOnPageChangeCallback(OnBoardingPageChangeCallback)
            (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        TabLayoutMediator(tabLayout, onBoardingViewPager2){tab ,position -> }.attach()

        nextBtn.setOnClickListener {
            if (onBoardingViewPager2.currentItem < onBoardingViewPager2.adapter!!.itemCount-1){
                onBoardingViewPager2.currentItem += 1
            } else {
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

    override fun onDestroy() {
        onBoardingViewPager2.unregisterOnPageChangeCallback(OnBoardingPageChangeCallback)
        super.onDestroy()
    }

    private fun homeScreenIntent() {
        val homeIntent = Intent(this, LastOnBoardingActivity::class.java)
        startActivity(homeIntent)
    }
}