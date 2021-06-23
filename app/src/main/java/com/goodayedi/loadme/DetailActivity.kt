package com.goodayedi.loadme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.motion.widget.MotionLayout

class DetailActivity : AppCompatActivity() {

    lateinit var fileTextView: TextView
    lateinit var statusTextView: TextView
    lateinit var okButton: Button
    lateinit var motion: MotionLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        fileTextView = findViewById(R.id.textView_file_name)
        statusTextView = findViewById(R.id.textView_status)
        okButton = findViewById(R.id.button_back)
        motion = findViewById(R.id.motion)

        val mainHandler = Handler(Looper.getMainLooper())
        mainHandler.postDelayed({
            motion.transitionToEnd()
        }, 100)

        val file = intent.getStringExtra("fileName")
        val status = intent.getStringExtra("status")

        fileTextView.text = file
        statusTextView.text = status

        okButton.setOnClickListener {
            finish()
        }

    }
}