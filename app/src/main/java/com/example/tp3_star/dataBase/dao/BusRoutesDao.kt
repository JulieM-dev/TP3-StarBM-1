package com.example.tp3_star.dataBase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.tp3_star.dataBase.entities.BusRoutes

@Dao
interface BusRoutesDao {
    @Query("SELECT * FROM busroutes")
    fun getAll() : List<BusRoutes>

    @Insert
    fun insertBusRoute(vararg busRoutes : BusRoutes)
}