package com.example.msapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false)
        val postEditText = view.findViewById<EditText>(R.id.postEditText)
        val saveButton = view.findViewById<ImageButton>(R.id.shareButton)

        saveButton.setOnClickListener {
            val postContent = postEditText.text.toString()

            // Handle saving the post content, e.g., save it to a database or send it to a server
            // You can implement the logic here

            // Close the bottom sheet
            dismiss()
        }

        return view
    }
}
