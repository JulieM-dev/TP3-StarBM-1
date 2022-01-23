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

    @Query("SELECT DISTINCT stops.stop_id, stop_name, stop_desc, stop_lat, stop_lon, wheelchair_boarding FROM stops INNER JOIN stoptimes ON stops.stop_id = stoptimes.stop_id INNER JOIN trips ON stoptimes.trip_id = trips.trip_id WHERE trips.route_id = :route_id AND direction_id = :direction_id ORDER BY stoptimes.stop_sequence")
    fun getFromTrip(route_id : String, direction_id : String) : Cursor

    @Query("SELECT stop_id, stop_name, stop_desc FROM stops WHERE stop_name LIKE '%' || :search || '%'")
    fun getFromSearch(search: String): Cursor

    @Query("DELETE FROM stops")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insertStops(stops : List<Stops>)
}