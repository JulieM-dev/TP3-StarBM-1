package com.example.tp3_star.dataBase

import android.content.Context
import androidx.room.Room
import com.example.tp3_star.dataBase.entities.BusRoutes
import com.example.tp3_star.dataBase.entities.DatabaseInfos

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

        insertDBInfos("1111", "https:///", true)
        System.out.println(databaseInfosDao.getInfos())
        busRoutes = busRoutesDao.getAll()
        System.out.println("----------- Récup BDD busRoutes : OK")
        System.out.println(busRoutes)
    }


    fun getRoutes() : List<BusRoutes>
    {
       return busRoutesDao.getAll()
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