package com.lappsmov.nailspasion.utils

import android.util.Log
import com.lappsmov.nailspasion.BuildConfig

object NLog {

    fun v(text:String?){
        if(BuildConfig.DEBUG){
            Log.v("NLog",text)
        }
    }
}