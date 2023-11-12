package com.example.msapplication

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DocumentsActivity : AppCompatActivity() {

    private val READ_EXTERNAL_STORAGE_REQUEST = 1
    private val PICK_DOCUMENT_REQUEST = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_documents)

        // Reference the Toolbar from the layout
        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        // Set the Toolbar as the support action bar
        setSupportActionBar(toolbar)

        // Enable the back button (up button) in the action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Handle the back button click event
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        val imageNoDocuments = findViewById<ImageView>(R.id.noDocuments_img)
        val fabAddDocument = findViewById<FloatingActionButton>(R.id.fabAddDocuments)

        // Check and request runtime permission
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                READ_EXTERNAL_STORAGE_REQUEST
            )
        } else {
            // Permission is already granted, proceed with loading documents
            loadDocuments()
        }

        fabAddDocument.setOnClickListener {
            openFilePicker()
        }
    }

    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*" // Set the MIME type to filter only documents
        startActivityForResult(intent, PICK_DOCUMENT_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_DOCUMENT_REQUEST && resultCode == Activity.RESULT_OK) {
            // Handle the selected document URI, e.g., upload it to your server or perform other actions
            val selectedDocumentUri = data?.data
            if (selectedDocumentUri != null) {
                // Do something with the selected document URI
                Toast.makeText(this, "Selected document: $selectedDocumentUri", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadDocuments() {
        // TODO: Implement logic to retrieve documents from storage and display in RecyclerView
        val documentsList = getDocumentsFromStorage()

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = DocumentsAdapter(documentsList)

        // Check if the documents list is empty and show/hide the ImageView accordingly
        val imageNoDocuments = findViewById<ImageView>(R.id.noDocuments_img)
        if (documentsList.isEmpty()) {
            imageNoDocuments.visibility = View.VISIBLE
        } else {
            imageNoDocuments.visibility = View.GONE
        }
    }

    private fun getDocumentsFromStorage(): List<Document> {
        val documentsList = mutableListOf<Document>()

        val uri = MediaStore.Files.getContentUri("external")

        val projection = arrayOf(
            MediaStore.Files.FileColumns.DISPLAY_NAME,
            MediaStore.Files.FileColumns.DATA
        )

        val selection = "${MediaStore.Files.FileColumns.MIME_TYPE}=?"
        val selectionArgs = arrayOf("application/pdf") // Filter by PDF MIME type

        val sortOrder = "${MediaStore.Files.FileColumns.DATE_ADDED} DESC"

        applicationContext.contentResolver.query(uri, projection, selection, selectionArgs, sortOrder)?.use { cursor ->
            val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME)
            val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA)

            while (cursor.moveToNext()) {
                val title = cursor.getString(nameColumn)
                val path = cursor.getString(dataColumn)
                documentsList.add(Document(title, path))
            }
        }

        return documentsList
    }

}