package com.lappsmov.nailspasion

import android.content.Context
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.nio.charset.Charset

class ControlFavorites {

    companion object {

        var ids_favoritos = ArrayList<Int>()
        var file_fav = File("")
        fun setFileFav(context: Context) {
            file_fav = File("${context.filesDir}/fav")
        }
    }

    var list_favorites = ArrayList<Utils.DataImages>()

    suspend fun readFavorites(only_ids: Boolean): ArrayList<Utils.DataImages>? {

        if (verificyExistFavFile()) {

            if (file_fav.length() > 0) {

                val jsonFav = JSONArray(file_fav.readText())

                list_favorites.clear()
                ids_favoritos.clear()

                for (i in 0 until jsonFav.length()) {

                    val jsonObject = jsonFav.getJSONObject(i)

                    if (only_ids) {
                        ids_favoritos.add(jsonObject.getString("id").toInt())
                    } else {
                        list_favorites.add(Utils.DataImages(jsonObject.getString("url_img"), jsonObject.getString("id")))
                        ids_favoritos.add(jsonObject.getString("id").toInt())
                    }
                }

                if (list_favorites.isNotEmpty()) return list_favorites

            }
        }
        return null
    }

    fun addFavorites(context: Context): Unit = runBlocking {

        // LEEMOS PRIMERO EL ARCHIVO FAVORITO, SE HACE EN COROUTINE PARA QUE NO SIGA EL CODIGO HASTA QUE LO LEA
        readFavorites(false)

        if (ids_favoritos.contains(FullImage.image.id.toInt())) {
            ids_favoritos.remove(FullImage.image.id.toInt())

            // ELIMINAR DEL ARRAY PRINCIPAL
            for (i in list_favorites) {
                if (i.id == FullImage.image.id) {
                    list_favorites.remove(i)
                    break
                }
            }
            escribirArchivoFav(list_favorites)
            Toast.makeText(context, context.getString(R.string.del_favorites), Toast.LENGTH_LONG).show()

        } else {

            ids_favoritos.add(FullImage.image.id.toInt())
            list_favorites.add(Utils.DataImages(FullImage.image.url_img, FullImage.image.id))
            escribirArchivoFav(list_favorites)
            Toast.makeText(context, context.getString(R.string.add_favorites), Toast.LENGTH_LONG).show()
        }
    }

    suspend fun escribirArchivoFav(datos: ArrayList<Utils.DataImages>) {

        delay(0)
        val jsonArray = JSONArray()
        for (i in datos) {

            val jsonChild = JSONObject()
            jsonChild.put("url_img", i.url_img)
            jsonChild.put("id", i.id)
            jsonArray.put(jsonChild)
        }

        file_fav.writeText(jsonArray.toString(), Charset.defaultCharset())
    }

    fun changedLogo(view: ImageView, element: Int, context: Context, anim: Boolean) {
        if (ids_favoritos.contains(element)) {
            if (anim) view.animation = AnimationUtils.loadAnimation(context, R.anim.add_fav_image)
            view.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.heart_off))
        } else {
            if (anim) view.animation = AnimationUtils.loadAnimation(context, R.anim.del_fav_image)
            view.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.heart_outline))
        }
    }

    fun verificyExistFavFile(): Boolean {
        if (!file_fav.exists()) file_fav.createNewFile()
        return true
    }

}