package com.lappsmov.nailspasion

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.transition.Transition
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider.getUriForFile
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_full_image.*
import kotlinx.coroutines.runBlocking
import uk.co.senab.photoview.PhotoViewAttacher
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.net.URL

class FullImage : AppCompatActivity() {

    companion object {
        lateinit var image: Utils.DataImages
        var activity_fav = false
        var hizo_cambios = false
        var pos = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_image)
        setSupportActionBar(toolbar_full_image)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = getString(R.string.title_details)

        Glide.with(this@FullImage).load(image.url_img).into(img_full)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            window.enterTransition.addListener(object : Transition.TransitionListener {
                override fun onTransitionEnd(transition: Transition?) {
                    // ACTIVA EL ZOOM EN LA IMAGEN
                    val photoViewAttacher = PhotoViewAttacher(img_full)
                    photoViewAttacher.update()
                }

                override fun onTransitionResume(transition: Transition?) {
                }

                override fun onTransitionPause(transition: Transition?) {
                }

                override fun onTransitionCancel(transition: Transition?) {
                }

                override fun onTransitionStart(transition: Transition?) {
                }
            })
        } else {
            Glide.with(this@FullImage).load(image.url_img).into(img_full)
            val photoViewAttacher = PhotoViewAttacher(img_full)
            photoViewAttacher.update()
        }

        ControlFavorites().changedLogo(heart_full_image, image.id.toInt(), this, false)
        ly_fav.setOnClickListener {
            runBlocking {
                ControlFavorites().addFavorites(this@FullImage)
                ControlFavorites().changedLogo(heart_full_image, image.id.toInt(), this@FullImage, true)
            }
            if (activity_fav) hizo_cambios = true
        }
        ly_share.setOnClickListener { sendImage().execute() }
    }

    inner class sendImage : AsyncTask<Void, Void, Void>() {

        lateinit var bitmap: Bitmap
        val bos = ByteArrayOutputStream()

        override fun onPreExecute() {
            super.onPreExecute()
            Toast.makeText(this@FullImage, getString(R.string.toast_compartiendo), Toast.LENGTH_LONG).show()
            share_full_image.animation = AnimationUtils.loadAnimation(this@FullImage, R.anim.rotation_share)
            share_full_image.animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?) {
                    share_full_image.visibility = View.GONE
                    progress_shared.visibility = View.VISIBLE
                }

                override fun onAnimationStart(animation: Animation?) {}
            })
        }

        override fun doInBackground(vararg params: Void?): Void? {
            bitmap = BitmapFactory.decodeStream(URL(image.url_img).openConnection().getInputStream())
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos)
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)

            val file = File("${cacheDir}/imgshared.jpg")
            try {

                file.createNewFile()
                val fos = FileOutputStream(file)
                fos.write(bos.toByteArray())

            } catch (error: Exception) {
            }

            startActivity(Intent.createChooser(Intent(Intent.ACTION_SEND).setType("image/jpeg")
                .putExtra(Intent.EXTRA_STREAM, getUriForFile(this@FullImage, "$packageName.fileprovider", file))
                .putExtra(Intent.EXTRA_TEXT, "${getString(R.string.share_text)} https://play.google.com/store/apps/details?id=$packageName ${getString(R.string.recomiendo)}"),
                getString(R.string.share_decorado)))

            progress_shared.visibility = View.GONE
            share_full_image.visibility = View.VISIBLE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
