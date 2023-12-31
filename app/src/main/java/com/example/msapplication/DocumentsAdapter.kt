package com.example.msapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.msapplication.data.domain.Document

class DocumentsAdapter(private val documents: List<Document>) :
    RecyclerView.Adapter<DocumentsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.documentTitleTextView)
        val detailsTextView: TextView = itemView.findViewById(R.id.documentDetailsTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_document, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val document = documents[position]
        holder.titleTextView.text = document.title
        holder.detailsTextView.text = document.details
    }

    override fun getItemCount(): Int {
        return documents.size
    }
}
