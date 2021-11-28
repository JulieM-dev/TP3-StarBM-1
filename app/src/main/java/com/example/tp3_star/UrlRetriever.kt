package com.example.tp3_star

import android.os.AsyncTask
import android.util.Log
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL
import java.net.URLConnection
import org.json.JSONException




class UrlRetriever : AsyncTask<String, Void, JSONObject>()
{
    @Override
    public override fun doInBackground(vararg url: String?): JSONObject {
        val str = "https://data.explore.star.fr/api/records/1.0/search/?dataset=tco-busmetro-horaires-gtfs-versions-td&q="
        var urlConn: URLConnection? = null
        var bufferedReader: BufferedReader? = null
        return try {
            val url = URL(str)
            urlConn = url.openConnection()
            bufferedReader = BufferedReader(InputStreamReader(urlConn.getInputStream()))
            val stringBuffer = StringBuffer()
            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                stringBuffer.append(line)
            }
            JSONObject(stringBuffer.toString())
        } catch (ex: Exception) {
            Log.e("App", "yourDataTask", ex)
            JSONObject()
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    @Override
    override fun onPostExecute(response: JSONObject?) {
        if(response != null)
        {
            try {
                System.out.println("--------------------------------------- RESPONSE : " + response.getString("publication") + " ---------------------------------------")

                Log.e("App", "Success: " + response.getString("publication"))
            } catch (ex: JSONException) {
                Log.e("App", "Failure", ex)
            }
        }
    }
}