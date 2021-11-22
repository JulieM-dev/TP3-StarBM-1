package com.example.tp3_star.dataBase.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StopTimes (
    @PrimaryKey val trip_id : Double,
    @ColumnInfo(name="arrival_time") val arrival_time : String,
    @ColumnInfo(name="departure_time") val departure_time : String,
    @ColumnInfo(name="stop_id") val stop_id : Int,
    @ColumnInfo(name="stop_sequence") val stop_sequence : Int,
)