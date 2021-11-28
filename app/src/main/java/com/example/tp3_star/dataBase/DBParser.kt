package com.example.tp3_star.dataBase

import android.content.Context
import com.example.tp3_star.dataBase.entities.BusRoutes
import com.example.tp3_star.dataBase.entities.Calendar
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


    fun strBool(string: String) : Boolean
    {
        return (string=="1")
    }


}