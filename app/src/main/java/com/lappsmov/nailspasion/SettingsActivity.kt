package com.lappsmov.nailspasion

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.lappsmov.nailspasion.utils.NLog
import com.lappsmov.nailspasion.utils.nCreateDialog
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.alert_validate.*
import com.android.volley.toolbox.RequestFuture
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit

class SettingsActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.ajustes)

        NLog.v("SettingsActivity")

        expli_compartir_app.text = "${getString(R.string.compartir)} ${getString(R.string.app_name)} ${getString(R.string.shared_with_friends)}"

        btn_validate.setOnClickListener {
            if (et_validation.text.length > 2) validationADS(et_validation.text.toString())
            else Toast.makeText(this, getString(R.string.validation), Toast.LENGTH_LONG).show()
        }
    }

    private fun validationADS(code: String) {
        //12345
        NLog.v("validationADS code $code")

        val dg = nCreateDialog(this, R.layout.alert_validate, false)
        dg.show()

        dg.tv_validate.text = getString(R.string.validando)
        dg.btn_ok_validate.visibility = View.GONE


        validateCodeASyncRequest(code,{

            NLog.v("success $it")
            when (it) {
                "1" -> {
                    dg.tv_validate.text = getString(R.string.validation_ok)
                    dg.btn_ok_validate.visibility = View.VISIBLE

                    dg.btn_ok_validate.setOnClickListener {
                        dg.dismiss()
                        startActivity(Intent(this@SettingsActivity, MainActivity::class.java)
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
        },{
            NLog.v("error $it")
            Toast.makeText(this,"Error $it",Toast.LENGTH_SHORT).show()
        })
        /*runBlocking {

            val result = withContext(Dispatchers.IO) {
                val response =validateCodeRequest(code)
                response
            }
            NLog.v("result $result")

            //val data = async { validatiosRequest(code) }
            //val data = async { validateCodeRequest(code) }
            //val response = data.await()
            //NLog.v("response $response")
            when (data.await()) {
                "1" -> {
                    dg.tv_validate.text = getString(R.string.validation_ok)
                    dg.btn_ok_validate.visibility = View.VISIBLE

                    dg.btn_ok_validate.setOnClickListener {
                        dg.dismiss()
                        startActivity(Intent(this@SettingsActivity, MainActivity::class.java)
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
        }*/
    }


    private fun validateCodeASyncRequest(code:String, callbackSuccess:(response:String?)->Unit,
                                         callbackError: (error: String?) -> Unit){

        val queue = Volley.newRequestQueue(this@SettingsActivity)
        val url = "${Utils().url_server}/validation_ads.php"
        val request = object : StringRequest(Method.POST, url, Response.Listener<String> {

            NLog.v( "it-> $it")
            callbackSuccess(it)

        }, Response.ErrorListener {
            callbackError(it.message)
        }) {
            override fun getParams(): MutableMap<String, String> {

                val params = hashMapOf<String, String>()
                params["code"] = code

                return params
            }
        }

        request.retryPolicy = DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        queue.add(request)
    }

    private suspend fun validateCodeSincRequest(code: String): String? {
        var response:String? = null
        val future = RequestFuture.newFuture<String>()
        val url = "${Utils().url_server}/validation_ads.php"
        val queue = Volley.newRequestQueue(this@SettingsActivity)
        val request = object:StringRequest(Method.POST, url,future,future){
            override fun getParams(): MutableMap<String, String> {
                val params = hashMapOf<String, String>()
                params["code"] = code
                return params
            }
        }

        //request.retryPolicy = DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        try {
            response = future.get(25, TimeUnit.SECONDS) // this will block
        } catch (e: Exception) {
            // exception handling
            NLog.v("error ${e.message ?: "Ocurrió un error"}")
        }
        NLog.v(" validateCodeRequest $response")

        queue.add(request)

        return response
    }

    suspend fun validatiosRequest(code: String): String {

        var rps = ""

        val queue = Volley.newRequestQueue(this@SettingsActivity)
        val url = "${Utils().url_server}/validation_ads.php"
        val request = object : StringRequest(Method.POST, url, Response.Listener<String> {
            rps = it
            NLog.v( "it-> $it")

        }, Response.ErrorListener { }) {
            override fun getParams(): MutableMap<String, String> {

                val params = hashMapOf<String, String>()
                params["code"] = code

                return params
            }
        }

        request.retryPolicy = DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
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
