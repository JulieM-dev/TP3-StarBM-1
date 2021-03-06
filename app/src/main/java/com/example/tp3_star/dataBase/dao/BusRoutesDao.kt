package com.example.tp3_star.dataBase.dao

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tp3_star.dataBase.entities.BusRoutes

@Dao
interface BusRoutesDao {
    @Query("SELECT * FROM busroutes")
    fun getAll() : List<BusRoutes>

    @Query("SELECT * FROM busroutes")
    fun getAllCursor() : Cursor

    @Query("SELECT DISTINCT busroutes.route_short FROM busroutes INNER JOIN trips ON trips.route_id = busroutes.route_id INNER JOIN stoptimes ON stoptimes.trip_id = trips.trip_id WHERE stoptimes.stop_id = :stop_id")
    fun getRoutesFromStop(stop_id: String): Cursor

    @Insert
    fun insertBusRoute(vararg busRoutes : BusRoutes)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insertBusRoutes(busRoutes : List<BusRoutes>)

    @Query("DELETE FROM busroutes")
    fun deleteAll()
}