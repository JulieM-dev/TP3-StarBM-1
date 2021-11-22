package com.example.tp3_star.dataBase.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.tp3_star.dataBase.entities.Trips

@Dao
interface TripsDao {
    @Query("SELECT * FROM trips")
    fun getAll() : List<Trips>
}