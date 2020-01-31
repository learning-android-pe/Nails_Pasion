package com.lappsmov.nailspasion.utils

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.lappsmov.nailspasion.R

fun AppCompatActivity.nCreateDialog(context:Context,layout: Int, cancelable: Boolean):Dialog{
    val dialog = Dialog(context, R.style.my_dialog_theme)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    val view = LayoutInflater.from(context).inflate(layout, null)
    dialog.setContentView(view)
    dialog.setCancelable(cancelable)
    return dialog
}

fun Fragment.nCreateDialog(context:Context,layout: Int, cancelable: Boolean):Dialog{
    val dialog = Dialog(context, R.style.my_dialog_theme)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    val view = LayoutInflater.from(context).inflate(layout, null)
    dialog.setContentView(view)
    dialog.setCancelable(cancelable)
    return dialog
}