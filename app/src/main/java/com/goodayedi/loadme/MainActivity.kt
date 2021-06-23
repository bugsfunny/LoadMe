package com.goodayedi.loadme

import android.app.DownloadManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.goodayedi.loadme.util.ButtonState
import com.goodayedi.loadme.util.cancelNotifications
import com.goodayedi.loadme.util.sendNotification

class MainActivity : AppCompatActivity() {

    companion object {
        private const val GLIDE_URL =
            "https://github.com/bumptech/glide/archive/master.zip"
        private const val LOAD_APP_URL =
            "https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter/archive/master.zip"
        private const val RETROFIT_URL =
            "https://github.com/square/retrofit/archive/master.zip"
    }

    private var downloadID: Long = 0

    private lateinit var downloadButton: DownloadButton
    private lateinit var progressBar: View
    private lateinit var radioGroup: RadioGroup
    private var selectedItem: String? = null

    private lateinit var downloadManager: DownloadManager
    private lateinit var notificationManager: NotificationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        notificationManager = ContextCompat.getSystemService(
            this,
            NotificationManager::class.java
        ) as NotificationManager

        registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
        createChannel(getString(R.string.app_name), getString(R.string.app_name))

        downloadButton = findViewById(R.id.button_download)
        progressBar = findViewById(R.id.progress_bar)
        radioGroup = findViewById(R.id.choice)


        downloadButton.setOnClickListener {
            when (radioGroup.checkedRadioButtonId) {
                R.id.glide -> {
                    selectedItem = getString(R.string.glide_description)
                    download(GLIDE_URL)
                    downloadButton.setState(ButtonState.Loading)
                }
                R.id.current -> {
                    selectedItem = getString(R.string.current_app_description)
                    download(LOAD_APP_URL)
                    downloadButton.setState(ButtonState.Loading)
                }
                R.id.retrofit -> {
                    selectedItem = getString(R.string.retrofit_description)
                    download(RETROFIT_URL)
                    downloadButton.setState(ButtonState.Loading)
                }
                else -> {
                    Toast.makeText(this, "Select File", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)

            var status: String? = null
            var downloadStatus: Int? = null

            val query = id.let { DownloadManager.Query().setFilterById(it) }
            val cursor = downloadManager.query(query)

            if (cursor.moveToFirst()) {
                downloadStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
            }


            when (downloadStatus) {
                DownloadManager.STATUS_SUCCESSFUL -> {
                    status = "Success"
                }
                DownloadManager.STATUS_FAILED -> {
                    status = "Fail"
                }
            }

            selectedItem?.let {
                status?.let { status ->
                    notificationManager.sendNotification(
                        context,
                        it,
                        status
                    )
                }
            }

            downloadButton.setState(ButtonState.Completed)
        }
    }

    private fun download(url: String) {
        val request =
            DownloadManager.Request(Uri.parse(url))
                .setTitle(getString(R.string.app_name))
                .setDescription(getString(R.string.app_description))

        // enqueue puts the download request in the queue.
        downloadID = downloadManager.enqueue(request)

    }

    private fun createChannel(channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val descriptionText = getString(R.string.app_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onResume() {
        super.onResume()
        downloadButton.setState(ButtonState.Clicked)
        notificationManager.cancelNotifications()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

}

