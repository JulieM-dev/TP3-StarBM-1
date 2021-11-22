package com.example.tp3_star.dataBase.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Calendar (
    @PrimaryKey val service_id : Double,
    @ColumnInfo(name="monday") val monday : Boolean,
    @ColumnInfo(name="tuesday") val tuesday : Boolean,
    @ColumnInfo(name="wednesday") val wednesday : Boolean,
    @ColumnInfo(name="thursday") val thursday : Boolean,
    @ColumnInfo(name="friday") val friday : Boolean,
    @ColumnInfo(name="saturday") val saturday : Boolean,
    @ColumnInfo(name="sunday") val sunday : Boolean,
    @ColumnInfo(name="start_date") val start_date : Double,
    @ColumnInfo(name="end_date") val end_date : Double,
)