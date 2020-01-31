package com.lappsmov.nailspasion

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.alert_validate.*
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class Settings : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = getString(R.string.ajustes)

        expli_compartir_app.text = "${getString(R.string.compartir)} ${getString(R.string.app_name)} ${getString(R.string.shared_with_friends)}"

        btn_validate.setOnClickListener {

            if (et_validation.text.length > 2) validationADS(et_validation.text.toString())
            else Toast.makeText(this, getString(R.string.validation), Toast.LENGTH_LONG).show()

        }

    }

    private fun validationADS(code: String) {

        val dg = Utils().createDialog(this, R.layout.alert_validate, false)
        dg.show()

        dg.tv_validate.text = getString(R.string.validando)
        dg.btn_ok_validate.visibility = View.GONE

        runBlocking {

            val data = async { validatiosRequest(code) }

            when (data.await()) {
                "1" -> {
                    dg.tv_validate.text = getString(R.string.validation_ok)
                    dg.btn_ok_validate.visibility = View.VISIBLE

                    dg.btn_ok_validate.setOnClickListener {
                        dg.dismiss()
                        startActivity(Intent(this@Settings, MainActivity::class.java)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    }
                }
                "0" -> {
                    dg.tv_validate.text = getString(R.string.validation_failed)
                    dg.btn_ok_validate.visibility = View.VISIBLE

                    dg.btn_ok_validate.setOnClickListener {
                        dg.dismiss()
                    }
                }
                else -> dg.tv_validate.text = "error"
            }
        }
    }

    suspend fun validatiosRequest(code: String): String {

        var rps = ""

        val queue = Volley.newRequestQueue(this@Settings)
        val url = "${Utils().url_server}/validation_ads.php"
        val request = object : StringRequest(Method.POST, url, Response.Listener<String> {
            rps = it
            Log.i("prueba", "it-> $it")

        }, Response.ErrorListener { }) {
            override fun getParams(): MutableMap<String, String> {

                val params = hashMapOf<String, String>()
                params["code"] = code

                return params
            }
        }

        request.retryPolicy = DefaultRetryPolicy()
        queue.add(request)

        return rps
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }

}
