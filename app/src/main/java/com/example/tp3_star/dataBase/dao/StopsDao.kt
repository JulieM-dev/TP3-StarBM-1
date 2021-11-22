package com.example.tp3_star.dataBase.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.tp3_star.dataBase.entities.Stops

@Dao
interface StopsDao {
    @Query("SELECT * FROM stops")
    fun getAll() : List<Stops>
}