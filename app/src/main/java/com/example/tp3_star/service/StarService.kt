package com.example.tp3_star.service

import android.app.IntentService
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.RingtoneManager
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import com.example.tp3_star.R

class StarService() : Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        System.out.println("--------------------------------------- Service lancé ---------------------------------------")
    }

    override fun onDestroy() {
        super.onDestroy()
        System.out.println("--------------------------------------- Service coupé ---------------------------------------")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        System.out.println("--------------------------------------- Commande lancée ---------------------------------------")



        if (intent != null) {
            showNotification(applicationContext, "Notif test", "Test de notification", intent, 0)
        }
        return super.onStartCommand(intent, flags, startId)
    }

    fun showNotification( context: Context, title: String, message: String, intent: Intent, reqCode: Int)
    {
        val intent = Intent(this, StarService::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        var pendingIntent = PendingIntent.getActivity(context, reqCode, intent, 0)
        val CHANNEL_ID = "notifs"
        var notifBuilder : NotificationCompat.Builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        val notifManager : NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notifManager.notify(reqCode, notifBuilder.build())

        System.out.println("--------------------------------------- Notif lancée ---------------------------------------")

    }


    fun checkUpdates()
    {

    }

}