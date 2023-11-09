package com.example.msapplication

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NotesActivity : AppCompatActivity(), NotesAdapter.OnNoteEditListener, NotesAdapter.OnNoteDeleteListener {

    private lateinit var notesAdapter: NotesAdapter
    private val notesList = mutableListOf<Note>()
    private val NOTES_KEY = "app_notes"
    private var editingPosition: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        val recyclerViewNotes = findViewById<RecyclerView>(R.id.recyclerViewNotes)
        val fabAddNote = findViewById<FloatingActionButton>(R.id.fabAddNote)

        notesAdapter = NotesAdapter(notesList, this, this)
        recyclerViewNotes.layoutManager = LinearLayoutManager(this)
        recyclerViewNotes.adapter = notesAdapter

        loadNotes()

        fabAddNote.setOnClickListener {
            showBottomSheet()
        }
    }

    private fun showBottomSheet() {
        val bottomSheetFragment = BottomSheetNotesFragment()
        bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
    }

    override fun onNoteEdit(position: Int) {
        val noteToEdit = notesList[position]
        enableEditingMode(noteToEdit, position)
    }

    private fun enableEditingMode(note: Note, position: Int) {
        editingPosition = position
        val bottomSheetFragment = BottomSheetNotesFragment()

        // Pass the note details and editing position to the fragment using arguments
        val bundle = Bundle()
        bundle.putString("title", note.title)
        bundle.putString("details", note.details)
        bundle.putBoolean("editMode", true)
        bundle.putInt("position", editingPosition) // Pass the editing position
        bottomSheetFragment.arguments = bundle

        // Show the BottomSheetNotesFragment
        bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
    }




    override fun onNoteDelete(position: Int) {
        notesList.removeAt(position)
        notesAdapter.notifyItemRemoved(position)
        saveNotes()
    }

    fun addNoteFromBottomSheet(title: String, details: String) {
        val newNote = Note(title, details)
        notesList.add(newNote)
        notesAdapter.notifyItemInserted(notesList.size - 1)
        saveNotes()
    }

    fun updateNoteFromBottomSheet(title: String, details: String, position: Int) {
        Log.d("NotesActivity", "Updating note - Title: $title, Details: $details, Position: $position")

        if (position >= 0 && position < notesList.size) {
            val editedNote = Note(title, details)
            notesList[position] = editedNote
            notesAdapter.notifyItemChanged(position)
            saveNotes()
        } else {
            Log.e("NotesActivity", "Invalid position: $position")
            // Handle the case where the position is invalid (e.g., -1)
            // You might want to log an error or show a message to the user.
        }
    }

    private fun saveNotes() {
        val sharedPreferences: SharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val notesSet = HashSet<String>()

        for (note in notesList) {
            notesSet.add("${note.title}: ${note.details}")
        }

        editor.putStringSet(NOTES_KEY, notesSet)
        editor.apply()
    }

    private fun loadNotes() {
        val sharedPreferences: SharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val notesSet = sharedPreferences.getStringSet(NOTES_KEY, HashSet<String>())

        notesList.clear()
        if (notesSet != null) {
            for (noteData in notesSet) {
                val noteArray = noteData.split(":").toTypedArray()
                if (noteArray.size == 2) {
                    val title = noteArray[0].trim()
                    val details = noteArray[1].trim()
                    val note = Note(title, details)
                    notesList.add(note)
                }
            }
        }

        notesAdapter.notifyDataSetChanged()
    }
}
