package com.example.msapplication

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.msapplication.data.domain.Page
import com.example.msapplication.ui.OnBoardingFragment

class OnBoardingAdapter(activity: FragmentActivity, private val pagerList: ArrayList<Page>): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return pagerList.size
    }

    override fun createFragment(position: Int): Fragment {
        return OnBoardingFragment(pagerList[position])
    }
}