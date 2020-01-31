package com.lappsmov.nailspasion

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.lappsmov.nailspasion.Fragments.DecoradoConvencional
import com.lappsmov.nailspasion.Fragments.DecoradoGel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var progressbar: ProgressBar
        var page_selected = 0
        var verifico_version = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar_main)

        progressbar = progressbar_main
        val adapterViewPager = AdapterFragmentPager(supportFragmentManager)

        adapterViewPager.addFragment(DecoradoConvencional(), getString(R.string.decorado_convencional))
        adapterViewPager.addFragment(DecoradoGel(), getString(R.string.decorado_gel))

        tabs.setupWithViewPager(view_pager)
        view_pager.adapter = adapterViewPager
        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                page_selected = position
            }
        })

        ControlFavorites.setFileFav(this)
        GlobalScope.launch { ControlFavorites().readFavorites(true) }
    }


    override fun onResume() {
        FullImage.activity_fav = false
        super.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_principal, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.favorites -> {
                startActivity(Intent(this, Favorites::class.java))
                return true // ENVIAR A FAVORITOS
            }
            R.id.settings -> {
                startActivity(Intent(this, Settings::class.java))
                return true // ENVIAR A AJUSTES
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    // ADAPTER PARA VIEWPAGER
    class AdapterFragmentPager(manager: FragmentManager) : FragmentPagerAdapter(manager) {

        val my_fragment_list = ArrayList<Utils.DataFragment>()

        override fun getItem(position: Int): Fragment = my_fragment_list[position].fragment

        override fun getCount(): Int = my_fragment_list.size

        fun addFragment(fragment: Fragment, title: String) =
            my_fragment_list.add(Utils.DataFragment(fragment, title))

        override fun getPageTitle(position: Int): CharSequence? {
            return my_fragment_list[position].title
        }
    }
}
