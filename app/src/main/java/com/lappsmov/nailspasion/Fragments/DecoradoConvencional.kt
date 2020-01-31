package com.lappsmov.nailspasion.Fragments

import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.lappsmov.nailspasion.BuildConfig
import com.lappsmov.nailspasion.MainActivity
import com.lappsmov.nailspasion.R
import com.lappsmov.nailspasion.Utils
import kotlinx.android.synthetic.main.alert_update.*
import kotlinx.android.synthetic.main.fragment_decorado_convencional.view.*
import org.json.JSONArray
import org.json.JSONObject

class DecoradoConvencional : Fragment() {

    var list_images = ArrayList<Utils.DataImages>()

    lateinit var view_root: View
    var data_convencional = ""
    lateinit var recyclerView_convencional: RecyclerView

    // VARIABLE PARA CONTROLAR SCROLL DEL RECYCLER
    var page = 0
    var loading = false
    var end_data = false
    lateinit var queue: RequestQueue

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        view_root = inflater.inflate(R.layout.fragment_decorado_convencional, container, false)

        queue = Volley.newRequestQueue(activity)
        recyclerView_convencional = view_root.recycler_convencional
        recyclerView_convencional.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!end_data) {
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val elemento_final = layoutManager.findLastVisibleItemPosition()

                    when {
                        elemento_final == recyclerView.adapter!!.itemCount - 10 && !loading -> {

                            loading = true
                            page++

                            getDataServer()
                        }
                    }
                }
            }
        })

        getDataServer()

        return view_root
    }

    suspend fun getVersion() {

        val url_conex = "${Utils().url_server}/get_version.php"
        val requestQueue = object : StringRequest(Method.POST, url_conex, Response.Listener<String> {



        }, Response.ErrorListener { Log.i("prueba", "error version -> ${it}") }) {}

        requestQueue.retryPolicy = DefaultRetryPolicy()
        queue.add(requestQueue)
    }

    private fun getDataServer() {

        MainActivity.progressbar.visibility = View.VISIBLE
        //val queue = Volley.newRequestQueue(activity)
        val request = object : StringRequest(Method.POST, "${Utils().url_server}/get_data_convencional.php", Response.Listener<String> {

            if (MainActivity.verifico_version) {
                if (it == "[]") {
                    end_data = true
                    Toast.makeText(activity, getString(R.string.llegamos_al_final), Toast.LENGTH_LONG).show()
                } else {
                    data_convencional = it
                    loadDataServer().execute()
                }
            }else {

                val json = JSONObject(it)
                data_convencional = json.optString("data_convencional")
                loadDataServer().execute()

                val json_version = JSONArray(json.optString("data_version"))

                val bundle_server = json_version.getJSONObject (0).getString("bundle_final").toInt()
                val url_download = json_version.getJSONObject (0).getString("url_download")

                if (BuildConfig.VERSION_CODE < bundle_server) {

                    val dg = Utils().createDialog(activity!!, R.layout.alert_update, false)
                    dg.show()

                    dg.btn_alert_ok.setOnClickListener {
                        dg.dismiss()
                        startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(url_download)))
                    }
                }
            }

        }, Response.ErrorListener {
            Log.i("prueba", "Error al cargar decorado convencional -> ${it.message}")
        }) {
            override fun getParams(): MutableMap<String, String> {
                val params = hashMapOf<String, String>()
                params["page"] = page.toString()
                params["version"] = MainActivity.verifico_version.toString()

                return params
            }
        }
        request.retryPolicy = DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        queue.add(request)
    }

    inner class loadDataServer : AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg params: Void?): Void? {

            if (data_convencional.isNotEmpty()) {

                val jsonArray = JSONArray(data_convencional)

                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    list_images.add(Utils.DataImages(jsonObject.getString("url"), jsonObject.getString("id")))
                }
            }

            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)

            if (loading) {
                recyclerView_convencional.adapter!!.notifyDataSetChanged()
                loading = false

            } else {
                recyclerView_convencional.layoutManager = GridLayoutManager(activity, resources.getInteger(R.integer.recycler_images))
                recyclerView_convencional.adapter = Utils.AdapterRecyclerImages(list_images, activity!!)
            }
            MainActivity.progressbar.visibility = View.GONE
        }
    }

}
