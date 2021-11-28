package com.example.tp3_star.dataBase

import android.content.Context
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.tp3_star.R
import com.example.tp3_star.dataBase.entities.*
import java.io.File
import java.io.InputStream

class DBParser(context: Context) {

    private val context = context
    private val BASE_FOLDER = (context.dataDir).toString()
    private val downloadPath = (BASE_FOLDER + File.separator
            + "star_temp" + File.separator)

    fun parseBusRoutes() : List<BusRoutes>
    {
        val retList = ArrayList<BusRoutes>()
        val inputStream: InputStream = File( downloadPath +"routes.txt").inputStream()
        var firstLine = true;
        inputStream.bufferedReader().forEachLine {
            if (!firstLine)
            {
                var line = it.replace("\"", "").split(",")
                retList.add(BusRoutes(
                    line.get(0).toInt(),
                    line.get(2),
                    line.get(3),
                    line.get(4),
                    line.get(5),
                    line.get(7),
                    line.get(8)))
            }
            else firstLine = false

        }
        return retList
    }

    fun parseCalendar() : List<Calendar>
    {
        val retList = ArrayList<Calendar>()
        val inputStream: InputStream = File( downloadPath +"calendar.txt").inputStream()
        var firstLine = true;
        inputStream.bufferedReader().forEachLine {
            if (!firstLine)
            {
                var line = it.replace("\"", "").split(",")
                retList.add(
                    Calendar(
                    line.get(0).toDouble(),
                    strBool(line.get(1)),
                    strBool(line.get(2)),
                    strBool(line.get(3)),
                    strBool(line.get(4)),
                    strBool(line.get(5)),
                    strBool(line.get(6)),
                    strBool(line.get(7)),
                    line.get(8).toDouble(),
                    line.get(9).toDouble())
                )
            }
            else firstLine = false

        }
        return retList
    }

    fun parseStopTimes() : List<StopTimes>
    {
        val retList = ArrayList<StopTimes>()
        val inputStream: InputStream = File( downloadPath +"stop_times.txt").inputStream()
        var firstLine = true;
        inputStream.bufferedReader().forEachLine {
            if (!firstLine)
            {
                var line = it.replace("\"", "").split(",")
                retList.add(
                    StopTimes(
                    line.get(0).toDouble(),
                    line.get(1),
                    line.get(2),
                    line.get(3).toInt(),
                    line.get(4).toInt())
                )
            }
            else firstLine = false

        }
        return retList
    }

    fun parseTrips() : List<Trips>
    {
        val retList = ArrayList<Trips>()
        val inputStream: InputStream = File( downloadPath +"trips.txt").inputStream()
        var firstLine = true;
        inputStream.bufferedReader().forEachLine {
            if (!firstLine)
            {
                var line = it.replace("\"", "").split(",")
                retList.add(
                    Trips(
                    line.get(0).toInt(),
                    line.get(1).toDouble(),
                    line.get(3),
                    line.get(5).toInt(),
                    line.get(6),
                    this.strBool(line.get(8)))
                )
            }
            else firstLine = false

        }
        return retList
    }

    fun parseStops() : List<Stops>
    {
        val retList = ArrayList<Stops>()
        val inputStream: InputStream = File( downloadPath +"stops.txt").inputStream()
        var firstLine = true;
        inputStream.bufferedReader().forEachLine {
            if (!firstLine)
            {
                var line = it.replace("\"", "").split(",")
                retList.add(Stops(
                    line.get(0).toInt(),
                    line.get(2),
                    line.get(3),
                    line.get(4).toDouble(),
                    line.get(5).toDouble(),
                    this.strBool(line.get(11))))
            }
            else firstLine = false

        }
        return retList
    }


    fun strBool(string: String) : Boolean
    {
        return (string=="1")
    }




}