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
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import com.example.tp3_star.NotificationStar
import com.example.tp3_star.R
import com.example.tp3_star.UnzipManager
import com.example.tp3_star.UrlRetriever
import com.example.tp3_star.dataBase.DBManager
import com.example.tp3_star.dataBase.entities.DatabaseInfos
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL
import java.net.URLConnection
import org.json.JSONException


class StarService() : Service() {
    private lateinit var dbManager : DBManager

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        System.out.println("--------------------------------------- Service lancé ---------------------------------------")
        dbManager = DBManager(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        System.out.println("--------------------------------------- Service coupé ---------------------------------------")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        System.out.println("--------------------------------------- Commande lancée ---------------------------------------")
        checkUpdates()

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
        var response : JSONObject? = null
        Thread {
            // do background stuff here
            response = getJsonObject()
            try {
                System.out.println("--------------------------------------- RESPONSE : " + response!!.toString() + " ---------------------------------------")

                val lastRecord = response!!.getJSONArray("records").get(0) as JSONObject
                Log.e("App", "Success: " + lastRecord.getJSONObject("fields").getString("publication") )
                val newPublication = lastRecord.getJSONObject("fields").getString("publication")
                System.out.println("--------------------------------------- "+ dbManager.getDBPublication() +" ---------------------------------------")
                if(newPublication != "" && newPublication != dbManager.getDBPublication())
                {
                    dbManager.insertDBInfos(newPublication, lastRecord.getJSONObject("fields").getString("url"), false)
                    val notifStar = NotificationStar()
                    notifStar.sendNotif(this)
                }
                else if(!dbManager.getIsDBDownloaded())
                {
                    val notifStar = NotificationStar()
                    notifStar.sendNotif(this)

                }
                else
                {
                    System.out.println("--------------------------------------- DBPublication déjà existante : " + newPublication + " ---------------------------------------")
                }
            } catch (ex: JSONException) {
                Log.e("App", "Failure", ex)
            }
        }.start()
    }



    fun getJsonObject() : JSONObject?
    {
        val str = "https://data.explore.star.fr/api/records/1.0/search/?dataset=tco-busmetro-horaires-gtfs-versions-td&q="
        var urlConn: URLConnection? = null
        var bufferedReader: BufferedReader? = null
        return try {
            val url = URL(str)
            urlConn = url.openConnection()
            bufferedReader = BufferedReader(InputStreamReader(urlConn.getInputStream()))
            val stringBuffer = StringBuffer()
            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                stringBuffer.append(line)
            }
            JSONObject(stringBuffer.toString())
        } catch (ex: Exception) {
            Log.e("App", "yourDataTask", ex)
            null
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }


}