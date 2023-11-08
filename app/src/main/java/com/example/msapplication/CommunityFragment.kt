package com.example.msapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CommunityFragment : Fragment() {

    private lateinit var bottomSheetFragment: BottomSheetFragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_community, container, false)
        val addPostBtn = view.findViewById<FloatingActionButton>(R.id.add_post)

        bottomSheetFragment = BottomSheetFragment() // Create an instance of my BottomSheetFragment

        addPostBtn.setOnClickListener {
            // Show the BottomSheetFragment when the button is clicked
            bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
        }

        return view
    }
}