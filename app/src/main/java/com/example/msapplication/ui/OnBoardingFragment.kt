package com.example.msapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.msapplication.R
import com.example.msapplication.data.domain.Page

class OnBoardingFragment(private val page: Page) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_on_boarding, container, false)

        val title = view.findViewById<TextView>(R.id.title_tv)
        val description = view.findViewById<TextView>(R.id.description_tv)
        val image = view.findViewById<ImageView>(R.id.imageView)

        title.text = page.title
        description.text = page.description
        image.setImageResource(page.img)


        return view
    }

}