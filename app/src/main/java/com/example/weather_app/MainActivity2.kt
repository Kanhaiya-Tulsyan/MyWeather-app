package com.example.weather_app

import android.content.Context
import android.content.SharedPreferences
import android.hardware.input.InputManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.text.DateFormat
import java.util.*

class MainActivity2 : AppCompatActivity() ,extradata.onDataAvailable{
    private val TAG = "MainActivity"
    lateinit var sharedpreferece: SharedPreferences
    private var symbol = "°"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fab.alpha=0.0f

        var calendar: Calendar = Calendar.getInstance()
        var currentdata:String= DateFormat.getDateInstance().format(calendar.getTime())
        date.text=currentdata.toString()


    }




    override fun onDataAvailable(data: datafile, tell: Boolean) {
        if (!tell) {
            sharedpreferece = getSharedPreferences("shared_pref", Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedpreferece.edit()

            cityname.text = data.name.toString()

            var tempe = data.main.temp.toString()
            var d = tempe.toDouble() - 273.0
            temp.text = d.toString().substring(0, 2) + symbol
            var min=data.main.temp_min.toString()
            var d1=(min.toDouble()-273).toString().substring(0,2)
            var max=data.main.temp_max.toString()
            var d2=(max.toDouble()-273).toString().substring(0,2)
            var d3=("$d1$symbol/$d2$symbol").toString()
            minmax.text=d3
            humidity.text=(data.main.humidity).toString()+"%"
            wind.text=(data.wind.speed).toString().substring(0,1)+"mph"
            rain.text=(data.clouds.all).toString()+"cm"
            type.text = data.weather[0].description.toString()
            when (data.weather[0].main) {
                "Clouds" -> homelayout.setBackgroundResource(R.drawable.cloudy)
                "Clear" -> homelayout.setBackgroundResource(R.drawable.sunny)
                "Rain" -> homelayout.setBackgroundResource(R.drawable.rainy)
                else -> homelayout.setBackgroundResource(R.drawable.bc)
            }

            editor.apply {
                putString("temp", temp.text as String)
                putString("city", cityname.text.toString())
                putString("rain",rain.text.toString())
                putString("humidity",humidity.text.toString())
                putString("wind",wind.text.toString())
            }.apply()
        } else {
            Toast.makeText(this, "Plz enter a valid city", Toast.LENGTH_SHORT).show()

        }
        close_keyboard(cityname)
    }

    fun searched(view: View) {
        var city = search1.text.toString()

        val url =
            "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=8ff2cff95f93a28558e32ccff79aff0f"
        try {
            val getRawData = extradata(this)
            getRawData.execute(url)
        } catch (e: Exception) {
            Toast.makeText(this, "Plz enter a valid city", Toast.LENGTH_SHORT).show()
        }

    }


     fun close_keyboard(view:View)
     {
         val imm:InputMethodManager=getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
         imm.hideSoftInputFromWindow(view.windowToken,0)


     }

}
