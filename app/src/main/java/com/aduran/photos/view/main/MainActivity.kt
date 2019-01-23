package com.aduran.photos.view.main

import android.os.Bundle
import com.aduran.photos.R
import com.aduran.photos.view.albumlist.AlbumListFragment
import com.aduran.photos.view.photolist.PhotoListFragment
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar!!.hide()

        if(savedInstanceState == null)
            supportFragmentManager.beginTransaction().add(R.id.container, AlbumListFragment()).commit()


    }
}
