package com.lappsmov.nailspasion

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lappsmov.nailspasion.model.DataImages
import kotlinx.android.synthetic.main.cv_imagen.view.*

class Utils {

    val url_server = "https://nailspasion.com/app"

    /*fun createDialog(context: Context, layout: Int, cancelable: Boolean): Dialog {

        val dialog = Dialog(context, R.style.my_dialog_theme)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val view = LayoutInflater.from(context).inflate(layout, null)
        dialog.setContentView(view)
        dialog.setCancelable(cancelable)
        return dialog
    }*/

    //ADAPTER PARA RECYCLERVIEW
    /*class AdapterRecyclerImages(val items: ArrayList<DataImages>, val context: Context) : RecyclerView.Adapter<AdapterRecyclerImages.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cv_imagen, parent, false))

        override fun getItemCount(): Int = items.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) =
            holder.bindItems(items[position], position)

        inner class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {

            fun bindItems(item: DataImages, position: Int) {

                Glide.with(context).load(item.url_img).into(itemView.img_card)
                itemView.id_img.text = item.id

                itemView.setOnClickListener {

                    FullImage.image = DataImages(item.url_img, item.id)
                    if (FullImage.activity_fav) FullImage.pos = position

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        val p01 = Pair.create(itemView.img_card as View, itemView.img_card.transitionName)
                        val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(context as Activity, p01)
                        context.startActivity(Intent(context, FullImage::class.java), activityOptions.toBundle())

                    } else context.startActivity(Intent(context, FullImage::class.java))

                }

            }
        }
    }*/
}