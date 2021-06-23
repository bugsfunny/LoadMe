package com.goodayedi.loadme.util

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.goodayedi.loadme.DetailActivity
import com.goodayedi.loadme.R

private const val NOTIFICATION_ID = 0
private const val REQUEST_CODE = 0

fun NotificationManager.sendNotification(
    applicationContext: Context,
    messageBody: String,
    status: String
) {

    val intent = Intent(applicationContext, DetailActivity::class.java).apply {
        putExtra("fileName", messageBody)
        putExtra("status", status)
    }
    val pendingIntent: PendingIntent =
        PendingIntent.getActivity(
            applicationContext,
            REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

    val action = NotificationCompat.Action(
        null,
        applicationContext.getString(R.string.check_status),
        pendingIntent
    )

    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.app_name)
    ).setSmallIcon(R.drawable.ic_baseline_cloud_download_24)
        .setContentTitle(applicationContext.getString(R.string.notification_title))
        .setContentText(messageBody)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setContentIntent(pendingIntent)
        .addAction(action)
        .setAutoCancel(true)

    notify(NOTIFICATION_ID, builder.build())
}

fun NotificationManager.cancelNotifications() {
    cancelAll()
}