package com.example.msapplication.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.msapplication.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetNotesFragment : BottomSheetDialogFragment() {

    private var editingPosition: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_notes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonSaveNote = view.findViewById<Button>(R.id.buttonSaveNote)
        val editTextTitle = view.findViewById<EditText>(R.id.editTextTitle)
        val editTextNoteDetails = view.findViewById<EditText>(R.id.editTextNoteDetails)

        // Check if the fragment is in edit mode
        val editMode = arguments?.getBoolean("editMode", false) ?: false

        if (editMode) {
            // If in edit mode, populate UI with existing note details
            val title = arguments?.getString("title", "")
            val details = arguments?.getString("details", "")
            editingPosition = arguments?.getInt("position", -1) ?: -1 // Explicitly set editingPosition

            editTextTitle.setText(title)
            editTextNoteDetails.setText(details)

            // Disable the save button until changes are made
            buttonSaveNote.isEnabled = false

            // Enable a TextWatcher to enable the save button when changes are made
            val textWatcher = object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    buttonSaveNote.isEnabled = true
                }
            }

            editTextTitle.addTextChangedListener(textWatcher)
            editTextNoteDetails.addTextChangedListener(textWatcher)
        }

        buttonSaveNote.setOnClickListener {
            val title = editTextTitle.text.toString()
            val details = editTextNoteDetails.text.toString()

            if (title.isNotEmpty() && details.isNotEmpty()) {
                if (editMode) {
                    // Notify the NotesActivity to update the note
                    (activity as? NotesActivity)?.updateNoteFromBottomSheet(title, details, editingPosition)
                } else {
                    // Notify the NotesActivity to add the note
                    (activity as? NotesActivity)?.addNoteFromBottomSheet(title, details)
                }

                dismiss()
            } else {
                // Handle empty input
            }
        }
    }
}