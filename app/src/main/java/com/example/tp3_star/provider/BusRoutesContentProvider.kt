package com.example.tp3_star.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.util.Log

import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import com.example.tp3_star.contract.StarContract
import com.example.tp3_star.contract.StarContract.AUTHORITY
import com.example.tp3_star.contract.StarContract.BusRoutes.CONTENT_PATH
import com.example.tp3_star.dataBase.DBManager
import java.lang.IllegalArgumentException
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


class BusRoutesContentProvider : ContentProvider(), StarContract.BusRoutes {


    override fun onCreate(): Boolean {
        return true
    }


    @RequiresApi(Build.VERSION_CODES.O)
    @Nullable
    override fun query(
        uri: Uri,
        @Nullable projection: Array<String?>?,
        @Nullable selection: String?,
        @Nullable selectionArgs: Array<String?>?,
        @Nullable sortOrder: String?
    ): Cursor? {
        if (context != null) {
            if(uri == StarContract.BusRoutes.CONTENT_URI)
            {
                val dbManager = DBManager(context!!)
                val cursor: Cursor = dbManager.getRoutesCursor()

                cursor.setNotificationUri(context!!.contentResolver, uri)
                return cursor
            }
            else if (uri == StarContract.Trips.CONTENT_URI)
            {
                if(selection != null)
                {
                    val dbManager = DBManager(context!!)
                    val cursor: Cursor = dbManager.getRouteDirections(selection)

                    cursor.setNotificationUri(context!!.contentResolver, uri)
                    return cursor
                }
                else
                {
                    throw IllegalArgumentException("route_id manquant en paramètre pour l'uri $uri")
                }
            }
            else if (uri == StarContract.Stops.CONTENT_URI)
            {
                if(selectionArgs != null)
                {
                    val dbManager = DBManager(context!!)
                    val cursor: Cursor = dbManager.getStopsCursor(selectionArgs.get(0)!!, selectionArgs.get(1)!!)

                    cursor.setNotificationUri(context!!.contentResolver, uri)
                    return cursor
                }
                else
                {
                    throw IllegalArgumentException("trip_id manquant en paramètre pour l'uri $uri")
                }
            }
            else if (uri == StarContract.StopTimes.CONTENT_URI)
            {
                if(selectionArgs != null)
                {
                    val dbManager = DBManager(context!!)

                    val stop_id = selectionArgs.get(0)
                    val route_id = selectionArgs.get(1)
                    val direction_id = selectionArgs.get(2)
                    // TODO récupérer depuis les paramètres

                    val date = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        LocalDate.parse(selectionArgs.get(3), DateTimeFormatter.ISO_LOCAL_DATE)
                    } else {
                        null
                    }
                    System.out.println(date)
                    Log.d("App", date.toString())

                    val heure = selectionArgs.get(4)


                    val cursor: Cursor = dbManager.getStopTimesCursor(stop_id!!, route_id!!, direction_id!!,
                        Date.from(date?.atStartOfDay(ZoneId.systemDefault())?.toInstant()), heure!!)

                    cursor.setNotificationUri(context!!.contentResolver, uri)
                    return cursor
                }
                else
                {
                    throw IllegalArgumentException("stop_id ou route_id manquant en paramètre pour l'uri $uri")
                }
            }


        }
        throw IllegalArgumentException("Failed to query row for uri $uri")
    }


    @Nullable
    override fun getType(uri: Uri): String? {
        return "vnd.android.cursor.item/$AUTHORITY.$CONTENT_PATH"
    }


    @Nullable
    override fun insert(uri: Uri, @Nullable contentValues: ContentValues?): Uri? {
        throw IllegalArgumentException("Failed to insert row into $uri")
    }


    override fun delete(uri: Uri, @Nullable s: String?, @Nullable strings: Array<String?>?): Int {
        throw IllegalArgumentException("Failed to delete row into $uri")
    }


    override fun update(
        uri: Uri,
        @Nullable contentValues: ContentValues?,
        @Nullable s: String?,
        @Nullable strings: Array<String?>?
    ): Int {
        throw IllegalArgumentException("Failed to update row into $uri")
    }


}