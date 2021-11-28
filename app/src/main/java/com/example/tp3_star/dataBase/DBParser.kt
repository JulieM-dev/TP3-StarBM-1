package com.example.tp3_star.dataBase

import android.content.Context
import com.example.tp3_star.dataBase.entities.BusRoutes
import java.io.File
import java.io.InputStream

class DBParser(context: Context) {

    private val context = context
    private val BASE_FOLDER = (context.dataDir).toString()
    private val downloadPath = "/data/data/com.example.tp3_star/star_temp/"

    fun parseBusRoutes() : List<BusRoutes>
    {
        val retList = ArrayList<BusRoutes>()
        val inputStream: InputStream = File( downloadPath +"routes.txt").inputStream()

        val streamLines = inputStream.bufferedReader().lines()
        streamLines.iterator().next()
        while (streamLines.iterator().hasNext())
        {
            var line = streamLines.iterator().next().replace("\"", "").split(",")
            retList.add(BusRoutes(
                line.get(0).toInt(),
                line.get(2),
                line.get(3),
                line.get(4),
                line.get(5),
                line.get(7),
                line.get(8)))

        }
        return retList
    }


}