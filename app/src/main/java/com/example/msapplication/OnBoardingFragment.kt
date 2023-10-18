package com.example.msapplication


import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class OnBoardingFragment(val page: Page) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_on_boarding, container, false)
        val color = if (isValidColorCode(page.color)) {
            Color.parseColor(page.color)
        } else {
            // Use a default color or handle the error gracefully
            // For example, you can use a default color like Color.WHITE
            Color.WHITE
        }
        view.setBackgroundColor(color)
        val title = view.findViewById<TextView>(R.id.title_tv!!)
        val description = view.findViewById<TextView>(R.id.description_tv!!)
        val image = view.findViewById<ImageView>(R.id.imageView!!)

        title.text = page.title
        description.text = page.description
        image.setImageResource(page.img)


        return view
    }

    fun isValidColorCode(colorCode: String): Boolean {
        // Check if the color code starts with '#' and has a valid length (6 or 8 characters)
        if (colorCode.isNotEmpty() && (colorCode.length == 7 || colorCode.length == 9) && colorCode[0] == '#') {
            // Check if the remaining characters are valid hexadecimal digits
            for (i in 1 until colorCode.length) {
                val c = colorCode[i]
                if (!(c in '0'..'9' || c in 'A'..'F' || c in 'a'..'f')) {
                    return false
                }
            }
            return true
        }
        return false
    }

}