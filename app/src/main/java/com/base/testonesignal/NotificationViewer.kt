package com.base.testonesignal

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.onesignal.OSNotificationReceivedEvent
import com.onesignal.OneSignal

open class NotificationViewer : OneSignal.OSRemoteNotificationReceivedHandler {
    override fun remoteNotificationReceived(p0: Context?, p1: OSNotificationReceivedEvent?) {
        val context = p0 ?: return
        val notification = p1?.notification ?: return
        val additionalData = notification.additionalData
        val target = additionalData?.optString("target", "")

        val intent = when (target) {
            "myapp://fragmentA" -> Intent(context, ActivityB::class.java)
            else -> Intent(context, MainActivity::class.java)
        }
        intent.putExtra("target", target)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Değiştirildi

        val uniqueRequestCode = System.currentTimeMillis().toInt() // Eklendi
        val pendingIntent = PendingIntent.getActivity(
            context,
            uniqueRequestCode, // Değiştirildi
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val channelId = "channel_01"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "Your Channel Name"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(channelId, channelName, importance)
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(notification.title)
            .setContentText(notification.body)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)  // Eklendi

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(uniqueRequestCode, builder.build()) // Değiştirildi
    }
}
