package com.example.tp3_star.dataBase.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.tp3_star.dataBase.entities.Calendar

@Dao
interface CalendarDao {
    @Query("SELECT * FROM calendar")
    fun getAll() : List<Calendar>
}