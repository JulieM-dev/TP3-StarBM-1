package com.example.tp3_star.dataBase

import android.content.Context
import android.database.Cursor
import androidx.room.Room
import com.example.tp3_star.dataBase.entities.BusRoutes
import com.example.tp3_star.dataBase.entities.DatabaseInfos
import java.lang.Exception

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

    fun getStopsCursor(trip_id: String) : Cursor
    {
        return stopsDao.getFromTrip(trip_id)
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