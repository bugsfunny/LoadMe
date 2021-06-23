package com.goodayedi.loadme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class DetailActivity : AppCompatActivity() {

    lateinit var fileTextView: TextView
    lateinit var statusTextView: TextView
    lateinit var okButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datail)

        fileTextView = findViewById(R.id.textView_file_name)
        statusTextView = findViewById(R.id.textView_status)
        okButton = findViewById(R.id.button_back)

        val file = intent.getStringExtra("fileName")
        val status = intent.getStringExtra("status")

        fileTextView.text = file
        statusTextView.text = status

        okButton.setOnClickListener {
            finish()
        }

    }
}