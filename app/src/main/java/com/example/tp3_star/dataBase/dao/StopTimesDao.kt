package com.example.tp3_star.dataBase.dao

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tp3_star.dataBase.entities.StopTimes
import com.example.tp3_star.dataBase.entities.Trips

@Dao
interface StopTimesDao {
    @Query("SELECT * FROM stoptimes LIMIT 100")
    fun getAll() : Cursor

    @Query("DELETE FROM stoptimes")
    fun deleteAll()

    @Query("SELECT DISTINCT * FROM stoptimes INNER JOIN trips ON stoptimes.trip_id = trips.trip_id WHERE stoptimes.stop_id = :stopId AND trips.route_id = :routeId AND trips.direction_id = :direction AND trips.service_id IN (:services_id) AND stoptimes.arrival_time >= :heure ORDER BY stoptimes.departure_time")
    fun getFromStopAndRoute(stopId: String, routeId: String, direction: String, services_id: List<Double>, heure : String): Cursor

    @Query("SELECT * FROM stoptimes INNER JOIN stops ON stoptimes.stop_id = stops.stop_id WHERE trip_id = :trip_id ORDER BY stop_sequence")
    fun getFromTrip(trip_id : String) : Cursor

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insertStopTimes(stoptimes : List<StopTimes>)
}