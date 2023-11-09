package com.example.msapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.cardview.widget.CardView

class NotesAdapter(
    private val notes: List<Note>,
    private val onNoteEditListener: OnNoteEditListener,
    private val onNoteDeleteListener: OnNoteDeleteListener
) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    interface OnNoteEditListener {
        fun onNoteEdit(position: Int)
    }

    interface OnNoteDeleteListener {
        fun onNoteDelete(position: Int)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardViewNote: CardView = itemView.findViewById(R.id.cardViewNote)
        val titleTextView: TextView = itemView.findViewById(R.id.textViewTitle)
        val detailsTextView: TextView = itemView.findViewById(R.id.textViewDetails)
        val editButton: ImageButton = itemView.findViewById(R.id.buttonEditNote)
        val deleteButton: ImageButton = itemView.findViewById(R.id.buttonDeleteNote)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notes[position]
        holder.titleTextView.text = note.title
        holder.detailsTextView.text = note.details

        holder.editButton.setOnClickListener {
            onNoteEditListener.onNoteEdit(position)
        }

        holder.deleteButton.setOnClickListener {
            onNoteDeleteListener.onNoteDelete(position)
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }
}
