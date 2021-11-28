package com.example.tp3_star.dataBase.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DatabaseInfos (
    @PrimaryKey val infos_id : Int,
    @ColumnInfo(name="publication_date") val publication_date : String,
    @ColumnInfo(name="origin_url") val origin_url : String,
    @ColumnInfo(name="downloaded") val downloaded : Boolean,
)