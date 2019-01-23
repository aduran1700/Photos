package com.aduran.photos.view.album

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aduran.photos.R
import com.aduran.photos.model.Album
import com.aduran.photos.model.FlickrPhoto
import com.aduran.photos.view.photolist.ItemSelectListener
import com.aduran.photos.view.photolist.PhotoListFragment
import com.aduran.photos.view.photolist.PhotoRecyclerViewAdapter
import com.aduran.photos.viewmodel.AlbumListViewModel

import com.aduran.photos.viewmodel.AlbumViewModel
import com.aduran.photos.viewmodel.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

private const val ARG_ALBUM_ID = "album_id"
class AlbumFragment : DaggerFragment(), ItemSelectListener {

    companion object {
        fun newInstance(id: Int) : AlbumFragment {
            val args = Bundle()
            args.putInt(ARG_ALBUM_ID, id)

            val fragment = AlbumFragment()
            fragment.arguments = args

            return fragment
        }
    }

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory<AlbumViewModel>
    private lateinit var viewModel: AlbumViewModel
    private lateinit var album: Album


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.album_fragment, container, false)
        val args = arguments

        viewModel = ViewModelProviders.of(this,viewModelFactory).get(AlbumViewModel::class.java)
        viewModel.getAlbum(args!!.getInt(ARG_ALBUM_ID))

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener { activity!!.onBackPressed() }

        val fab: FloatingActionButton = view.findViewById(R.id.fab)
        val listView = view.findViewById<RecyclerView>(R.id.list)
        listView.layoutManager = LinearLayoutManager(activity!!.applicationContext)

        val adapter = PhotoRecyclerViewAdapter(this)
        listView.adapter = adapter

        viewModel.getAblum().observe(this, Observer<Album> {
            if (it != null) {
                album = it
                toolbar.title = it.title
                listView.visibility = View.VISIBLE
                adapter.updateList(it.photos)
            }
        })

        fab.setOnClickListener {
            activity!!.supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, PhotoListFragment.newInstance(album))
                .addToBackStack("Photos")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
        }


        return view
    }


    override fun onItemSelection(photo: FlickrPhoto) {

    }

}
