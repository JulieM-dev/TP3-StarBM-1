package com.example.tp3_star

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.app.NotificationCompat
import androidx.core.view.isVisible
import androidx.room.Room
import com.example.tp3_star.dataBase.AppDatabase
import com.example.tp3_star.dataBase.DBManager
import com.example.tp3_star.dataBase.dao.BusRoutesDao
import com.example.tp3_star.dataBase.entities.BusRoutes
import com.example.tp3_star.dataBase.entities.DatabaseInfos
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var dbManager : DBManager
    private lateinit var selectedBusRoute : BusRoutes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val alarm = AlarmReceiver()
        alarm.setAlarm(this)
        dbManager = DBManager(this)
        dbManager.initTest()
        this.initChangeHour()
        this.initChangeDate()
        this.initSpinnerLignesBus()
        try {
            dbManager.getDBPublication()
        }
        catch (e : Exception)
        {
            downloadData()
        }

        if(intent.hasExtra("download"))
        {
            intent.replaceExtras(Bundle())
            downloadData()
        }
    }

    fun downloadData()
    {
        val unzipManager = UnzipManager(this, dbManager.getDBUrl())
        unzipManager.startUnzipping(1)
        System.out.println("Data downloaded  ------------------------------------------------------------")
    }

    fun initChangeHour(){
        val butChangeHour = this.findViewById<Button>(R.id.butChangeHour)
        val textViewHour = this.findViewById<TextView>(R.id.textViewHour)

        butChangeHour.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                textViewHour.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }
    }

    fun initChangeDate(){
        val butChangeDate = this.findViewById<Button>(R.id.butChangeDate)
        val textViewDate = this.findViewById<TextView>(R.id.textViewDate)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        butChangeDate.setOnClickListener {
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                textViewDate.setText("" + dayOfMonth + " " + month + ", " + year)
            }, year, month, day)
            dpd.show()
        }
    }

    fun initSpinnerLignesBus(){
        val spinnerLignesBus = this.findViewById<Spinner>(R.id.spinnerLignesBus)
        val busRoutes: List<BusRoutes> = dbManager.getRoutes()

        val adapter = CustomAdapter(this, busRoutes)
        spinnerLignesBus.onItemSelectedListener = this
        spinnerLignesBus.adapter = adapter
    }

    fun initDirections() {
        val spinnerDirections = this.findViewById<Spinner>(R.id.spinnerDirections)

        val list: List<String> = this.selectedBusRoute.route_long_name.split("<>")

        val adapter = ArrayAdapter<String>(this,
            android.R.layout.simple_spinner_item,
            list);
        spinnerDirections.adapter = adapter
    }

    override fun onItemSelected(adaptor: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val busRoutes: List<BusRoutes> = dbManager.getRoutes()
        this.selectedBusRoute = busRoutes.get(position)
        this.initDirections()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }



}