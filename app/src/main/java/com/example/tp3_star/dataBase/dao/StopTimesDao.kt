package com.example.tp3_star.dataBase.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.tp3_star.dataBase.entities.StopTimes

@Dao
interface StopTimesDao {
    @Query("SELECT * FROM stoptimes")
    fun getAll() : List<StopTimes>
}