package com.example.tp3_star.dataBase

import android.content.Context
import android.database.Cursor
import android.widget.Switch
import androidx.room.Room
import com.example.tp3_star.dataBase.entities.BusRoutes
import com.example.tp3_star.dataBase.entities.DatabaseInfos
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DBManager (applicationContext : Context) {

    val db = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java, "star-database"
    ).allowMainThreadQueries().fallbackToDestructiveMigration().build()

    val busRoutesDao = db.busRoutesDao()
    val calendarDao = db.calendarDao()
    val databaseInfosDao = db.databaseInfosDao()
    val stopsDao = db.stopsDao()
    val stopTimesDao = db.stopTimesDao()
    val tripsDao = db.tripsDao()

    val context = applicationContext

    fun initTest()
    {
        var busRoutes: List<BusRoutes> = this.busRoutesDao.getAll()

        System.out.println("----------- Création de la BDD : OK")
        System.out.println(busRoutes)
        try {
            if(getDBPublication() == "")
            {
                insertDBInfos("1111", "http://ftp.keolis-rennes.com/opendata/tco-busmetro-horaires-gtfs-versions-td/attachments/GTFS_2020.3.1_20211108_20211219.zip", true)
            }
        }
        catch (e : Exception)
        {
            insertDBInfos("1111", "http://ftp.keolis-rennes.com/opendata/tco-busmetro-horaires-gtfs-versions-td/attachments/GTFS_2020.3.1_20211108_20211219.zip", true)
        }


        System.out.println(databaseInfosDao.getInfos())
        busRoutes = busRoutesDao.getAll()
        System.out.println("----------- Récup BDD busRoutes : OK")
        System.out.println(busRoutes)
    }


    fun getRoutes() : List<BusRoutes>
    {
       return busRoutesDao.getAll()
    }


    fun getRoutesCursor() : Cursor
    {
        return busRoutesDao.getAllCursor()
    }

    fun getRoutesCursorFromStop(selection: String): Cursor {
        return busRoutesDao.getRoutesFromStop(selection)
    }

    fun getStopsCursor(route_id: String, direction_Id: String) : Cursor
    {
        return stopsDao.getFromTrip(route_id, direction_Id)
    }


    fun getStopsSearchCursor(search: String): Cursor {
        return stopsDao.getFromSearch(search)
    }

    fun getStopTimesCursor(
        stopId: String,
        routeId: String,
        directionId: String,
        date: Date,
        heure: String
    ): Cursor {
        val calendar = Calendar.getInstance()
        calendar.time = date
        val day = calendar.get(Calendar.DAY_OF_WEEK)
        val calendars = calendarDao.getAll()
        val calIds = ArrayList<Double>()
        date.time
        calendars.forEach{
            when(day)
            {
                Calendar.MONDAY -> if (it.monday) calIds.add(it.service_id)
                Calendar.TUESDAY -> if (it.tuesday) calIds.add(it.service_id)
                Calendar.WEDNESDAY -> if (it.wednesday) calIds.add(it.service_id)
                Calendar.THURSDAY -> if (it.thursday) calIds.add(it.service_id)
                Calendar.FRIDAY -> if (it.friday) calIds.add(it.service_id)
                Calendar.SATURDAY -> if (it.saturday) calIds.add(it.service_id)
                Calendar.SUNDAY -> if (it.sunday) calIds.add(it.service_id)
            }
        }

        return stopTimesDao.getFromStopAndRoute(stopId, routeId, directionId, calIds, heure)
    }

    fun getStopTimesCursorFromTrip(trip_id: String) : Cursor
    {
        return stopTimesDao.getFromTrip(trip_id)
    }

    fun getRouteDirections(route_id : String) : Cursor
    {
        return tripsDao.getRouteDirections(route_id)
    }



    fun insertRoute(busRoutes : BusRoutes)
    {
        busRoutesDao.insertBusRoute(busRoutes)
    }

    fun getDBPublication() : String
    {
        return databaseInfosDao.getInfos().publication_date
    }

    fun getIsDBDownloaded() : Boolean
    {
        return databaseInfosDao.getInfos().downloaded
    }

    fun getDBUrl() : String
    {
        return databaseInfosDao.getInfos().origin_url
    }

    fun insertDBInfos(publication: String, originUrl : String, isDownloaded : Boolean)
    {
        databaseInfosDao.deleteAll()
        databaseInfosDao.insertDatabaseInfos(DatabaseInfos(1, publication, originUrl, isDownloaded))
    }


}