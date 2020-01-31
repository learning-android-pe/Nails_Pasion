package com.lappsmov.nailspasion.Fragments

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.lappsmov.nailspasion.MainActivity
import com.lappsmov.nailspasion.R
import com.lappsmov.nailspasion.Utils
import com.lappsmov.nailspasion.adapters.ImagesAdapter
import com.lappsmov.nailspasion.model.DataImages
import kotlinx.android.synthetic.main.fragment_decorado_gel.view.*
import org.json.JSONArray

class DecoradoGel : Fragment() {

    var list_images = ArrayList<DataImages>()

    var data_gel = ""
    lateinit var recyclerView_gel: RecyclerView

    // VARIABLE PARA CONTROLAR SCROLL DEL RECYCLER
    var page = 0
    var loading = false
    var end_data = false

    // CONTROLAR LA PRIMERA CARGA
    var ready_to_start = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_decorado_gel, container, false)

        recyclerView_gel = view.recycler_gel
        recyclerView_gel.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!end_data) {
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val elemento_final = layoutManager.findLastVisibleItemPosition()
                    recyclerView.adapter?.let {
                        when {
                            elemento_final == it.itemCount - 10 && !loading -> {

                                loading = true
                                page++
                                getDataServer()
                            }
                        }
                    }

                }
            }
        })

        return view
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && !ready_to_start){
            getDataServer()
            ready_to_start = true
        }
    }

    fun getDataServer() {

        MainActivity.progressbar.visibility = View.VISIBLE
        val queue = Volley.newRequestQueue(activity)
        val request = object : StringRequest(Method.POST, "${Utils().url_server}/get_data_gel.php", Response.Listener<String> {

            if (it == "[]") {
                end_data = true
                MainActivity.progressbar.visibility = View.GONE
                Toast.makeText(activity, getString(R.string.llegamos_al_final), Toast.LENGTH_LONG).show()

            } else {
                data_gel = it
                loadDataServer().execute()
            }

        }, Response.ErrorListener {
            Log.i("prueba", "Error al cargar decorado gel -> ${it.message}")
        }) {
            override fun getParams(): MutableMap<String, String> {
                val params = hashMapOf<String, String>()
                params["page"] = page.toString()

                return params
            }
        }
        request.retryPolicy = DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        queue.add(request)
    }

    inner class loadDataServer : AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg params: Void?): Void? {

            if (data_gel.isNotEmpty()) {

                val jsonArray = JSONArray(data_gel)

                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    list_images.add(DataImages(jsonObject.getString("url"), jsonObject.getString("id")))
                }
            }

            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)

            if (loading) {
                recyclerView_gel.adapter?.notifyDataSetChanged()
                loading = false

            } else {
                activity?.let {
                    recyclerView_gel.layoutManager = LinearLayoutManager(it, LinearLayoutManager.VERTICAL, false)
                    recyclerView_gel.adapter = ImagesAdapter(list_images,it){

                    }
                }

            }
            MainActivity.progressbar.visibility = View.GONE
        }
    }


}
