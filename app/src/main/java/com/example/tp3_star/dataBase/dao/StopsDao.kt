package com.example.tp3_star.dataBase.dao

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tp3_star.dataBase.entities.StopTimes
import com.example.tp3_star.dataBase.entities.Stops

@Dao
interface StopsDao {
    @Query("SELECT * FROM stops")
    fun getAll() : List<Stops>

    @Query("SELECT * FROM stops INNER JOIN stoptimes ON stops.stop_id = stoptimes.stop_id WHERE trip_id = :trip_id")
    fun getFromTrip(trip_id : String) : Cursor


    @Query("DELETE FROM stops")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insertStops(stops : List<Stops>)
}