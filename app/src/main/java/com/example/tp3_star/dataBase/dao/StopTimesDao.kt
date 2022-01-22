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

    @Query("SELECT DISTINCT * FROM stoptimes INNER JOIN trips ON stoptimes.trip_id = trips.trip_id WHERE stoptimes.stop_id = :stopId AND trips.route_id = :routeId AND trips.direction_id = :direction AND trips.service_id IN (SELECT service_id FROM calendar WHERE monday = :monday AND tuesday = :tuesday AND wednesday = :wednesday AND thursday = :thursday AND friday = :friday AND saturday = :saturday AND sunday = :sunday ) ORDER BY stoptimes.departure_time")
    fun getFromStopAndRoute(stopId: String, routeId: String, direction: String, monday: String, tuesday: String, wednesday: String, thursday: String, friday: String, saturday: String, sunday: String): Cursor

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insertStopTimes(stoptimes : List<StopTimes>)
}