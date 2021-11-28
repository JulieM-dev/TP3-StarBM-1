package com.example.tp3_star.dataBase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.tp3_star.dataBase.entities.BusRoutes
import com.example.tp3_star.dataBase.entities.DatabaseInfos

@Dao
interface DatabaseInfosDao {
    @Query("SELECT * FROM databaseinfos ORDER BY infos_id DESC LIMIT 1")
    fun getInfos() : DatabaseInfos

    @Insert
    fun insertDatabaseInfos(vararg databaseInfos: DatabaseInfos)

    @Query("DELETE FROM databaseinfos")
    fun deleteAll()
}