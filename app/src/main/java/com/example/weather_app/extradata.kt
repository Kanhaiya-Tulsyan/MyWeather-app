package com.example.weather_app



import android.os.AsyncTask
import com.google.gson.Gson
import android.util.Log
import android.view.View
import android.widget.Toast

import com.google.gson.GsonBuilder
import java.lang.Exception
import java.net.URL

class extradata(val listener: onDataAvailable): AsyncTask<String, Void ,String>() {
    private val TAG = "GetRawData"
    private var teller:Boolean=false
    interface onDataAvailable{


        fun onDataAvailable(data: datafile, tell:Boolean)
    }
    override fun onPostExecute(result: String?) {
        if (teller == false) {
            val gson = Gson()
            val data1 = gson.fromJson(result, datafile::class.java)
            listener.onDataAvailable(data1,teller)
            Log.d(TAG, data1.toString())
        }

    }
    override fun doInBackground(vararg params: String?): String {
        if(params[0]!=null)
        {
            try{
            return URL(params[0]).readText()
            }
            catch(e :Exception)
            {
                teller=true

            }

        }
        return "no url specified"
    }

}
