package com.example.tp3_star.dataBase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tp3_star.dataBase.entities.BusRoutes
import com.example.tp3_star.dataBase.entities.Trips

@Dao
interface TripsDao {
    @Query("SELECT * FROM trips")
    fun getAll() : List<Trips>

    @Query("DELETE FROM trips")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insertTrips(trips : List<Trips>)
}