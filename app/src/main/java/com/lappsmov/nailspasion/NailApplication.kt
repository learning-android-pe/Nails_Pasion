package com.lappsmov.nailspasion

import android.app.Application
import android.util.Log
import com.android.volley.VolleyLog

class NailApplication:Application() {

    override fun onCreate() {
        super.onCreate()
        VolleyLog.setTag("Volley")
        Log.isLoggable("Volley", Log.VERBOSE)
    }
}