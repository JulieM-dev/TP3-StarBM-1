package com.example.tp3_star.dataBase.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys = ["trip_id"])
data class Trips (
    @ColumnInfo(name="route_id") val route_id : Int,
    @ColumnInfo(name="service_id") val service_id : Double,
    @ColumnInfo(name="trip_headsign") val trip_headsign : String,
    @ColumnInfo(name="direction_id") val direction_id : Int,
    @ColumnInfo(name="block_id") val block_id : String,
    @ColumnInfo(name="wheelchair_accessible") val wheelchair_accessible : Boolean,
    @ColumnInfo(name="trip_id") val trip_id : Double,
)