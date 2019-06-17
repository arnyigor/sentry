package com.arny.core

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.support.annotation.RequiresApi
import android.text.Html
import android.text.Spanned


fun isPermissionsGranted(permissions: Array<String>, grantResults: IntArray): Boolean {
    return !permissions.indices.map { grantResults[it] }.contains(PackageManager.PERMISSION_DENIED)
}

@RequiresApi(Build.VERSION_CODES.O)
private fun createNotificationChannel(context: Context): String {
    val channelId = "my_service"
    val channelName = "My Background Service"
    val chan = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_NONE)
    chan.lightColor = Color.BLUE
    chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
    val service = context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
    service?.createNotificationChannel(chan)
    return channelId
}

private fun getServiceNotification(context: Context, cls: Class<*>, requestCode: Int, title: String, content: String, icon: Int): Notification {
    val notification: Notification
    val notificationIntent = Intent(context, cls)
    notificationIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
    val pendingIntent = PendingIntent.getActivity(context, requestCode, notificationIntent, 0)
    val notifbuild = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        Notification.Builder(context, createNotificationChannel(context))
    } else {
        Notification.Builder(context)
    }
            .setSmallIcon(icon)// маленькая иконка
            .setAutoCancel(false)
            .setContentTitle(title)// Заголовок уведомления
            .setContentText(content) // Текст уведомления
    notifbuild.setContentIntent(pendingIntent)
    notification = notifbuild.build()
    return notification
}

fun createNotification(context: Context, cls: Class<*>, notifyId: Int, title: String, content: String = "", icon: Int, request: Int = 999) {
    val notification = getServiceNotification(context, cls, request, title, content, icon)
    val mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
    mNotificationManager?.notify(notifyId, notification)
}

fun fromHtml(html: String): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY) else Html.fromHtml(html)
}

fun String?.parseLong(): Long? {
    return when {
        this == null -> null
        this.isBlank() -> null
        else -> {
            try {
                this.toLong()
            } catch (e: Exception) {
                null
            }
        }
    }
}

fun String?.parseDouble(): Double? {
    return when {
        this == null -> null
        this.isBlank() -> null
        else -> {
            try {
                this.toDouble()
            } catch (e: Exception) {
                null
            }
        }
    }
}

fun String?.parseInt(): Int? {
    return when {
        this == null -> null
        this.isBlank() -> null
        else -> {
            try {
                this.toInt()
            } catch (e: Exception) {
                null
            }
        }
    }
}
