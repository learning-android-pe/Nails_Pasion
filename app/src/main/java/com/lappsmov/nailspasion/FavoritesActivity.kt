package com.lappsmov.nailspasion

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.lappsmov.nailspasion.adapters.ImagesAdapter
import com.lappsmov.nailspasion.model.DataImages
import kotlinx.android.synthetic.main.activity_favorites.*
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class FavoritesActivity : AppCompatActivity() {

   var job: Deferred<ArrayList<DataImages>?>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.favorites)
        FullImage.activity_fav = true

        if (ControlFavorites.file_fav.exists() && ControlFavorites.file_fav.length() > 0) {
            runBlocking {
                job = async { ControlFavorites().readFavorites(false) }
                progress_fav.visibility = View.GONE
                recycler_favorites.layoutManager = GridLayoutManager(this@FavoritesActivity, resources.getInteger(R.integer.recycler_images))

                recycler_favorites.adapter = ImagesAdapter(job?.await()?.reversed() as ArrayList<DataImages>, this@FavoritesActivity){

                }
            }
        }
    }

    override fun onResume() {
        if (FullImage.hizo_cambios) recycler_favorites.adapter?.notifyItemRemoved(FullImage.pos)
        super.onResume()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            onBackPressed()
            true
        } else super.onOptionsItemSelected(item)
    }
}
