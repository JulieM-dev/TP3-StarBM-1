package com.example.tp3_star.dataBase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tp3_star.dataBase.entities.StopTimes
import com.example.tp3_star.dataBase.entities.Trips

@Dao
interface StopTimesDao {
    @Query("SELECT * FROM stoptimes")
    fun getAll() : List<StopTimes>

    @Query("DELETE FROM stoptimes")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insertStopTimes(stoptimes : List<StopTimes>)
}