package com.example.tp3_star.dataBase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tp3_star.dataBase.entities.Calendar
import com.example.tp3_star.dataBase.entities.Stops

@Dao
interface CalendarDao {
    @Query("SELECT * FROM calendar")
    fun getAll() : List<Calendar>

    @Query("DELETE FROM calendar")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insertCalendars(stops : List<Calendar>)

}