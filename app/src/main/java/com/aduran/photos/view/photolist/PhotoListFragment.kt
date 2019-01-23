package com.aduran.photos.view.photolist

import android.app.SearchManager
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.aduran.photos.R
import com.aduran.photos.model.Album
import com.aduran.photos.model.FlickrPhoto
import com.aduran.photos.viewmodel.PhotoListViewModel
import com.aduran.photos.viewmodel.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject


private const val ARG_ALBUM = "album"
class PhotoListFragment : DaggerFragment(), ItemSelectListener {

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory<PhotoListViewModel>
    private lateinit var viewModel: PhotoListViewModel
    private lateinit var listView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var album: Album
    private lateinit var queryTextListener: SearchView.OnQueryTextListener
    private lateinit var toolbar: Toolbar
    private var isTagSearch = false

    companion object {
        fun newInstance(album: Album) : PhotoListFragment {
            val args = Bundle()
            args.putParcelable(ARG_ALBUM, album)

            val fragment = PhotoListFragment()
            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.photo_list_fragment, container, false)
        val fab = view.findViewById<FloatingActionButton>(R.id.fab)

        toolbar = view.findViewById(R.id.toolbar)
        setUpToolBar()
        val args = arguments
        album = args!!.getParcelable(ARG_ALBUM)

        listView = view.findViewById(R.id.list)
        listView.layoutManager = LinearLayoutManager(activity!!.applicationContext)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(PhotoListViewModel::class.java)
        val adapter = PhotoRecyclerViewAdapter(this)
        listView.adapter = adapter

        fab.setOnClickListener {
            when (isTagSearch) {
                false -> {isTagSearch = true
                    Toast.makeText(context, getString(R.string.search_tag_text), Toast.LENGTH_LONG).show() }
                true -> {isTagSearch = false
                    Toast.makeText(context, getString(R.string.search_by_text), Toast.LENGTH_LONG).show()}
            }
        }

        viewModel.getPhotos().observe(this, Observer<List<FlickrPhoto>> {
            if (it != null) {
                listView.visibility = View.VISIBLE
                adapter.updateList(it)
            }

        })

        return view
    }

    private fun setUpToolBar() {
        toolbar.title = getString(R.string.app_name)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener { activity!!.onBackPressed() }
        toolbar.inflateMenu(R.menu.menu_search)

        val searchItem = toolbar.menu.findItem(R.id.action_search)
        val searchManager = activity!!.getSystemService(Context.SEARCH_SERVICE) as SearchManager

        if (searchItem != null) {
            searchView = searchItem.actionView as SearchView
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(activity!!.componentName))

            queryTextListener = object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String): Boolean {
                    viewModel.searchPhotos(newText, isTagSearch)

                    return true
                }

                override fun onQueryTextSubmit(query: String): Boolean {
                    viewModel.searchPhotos(query, isTagSearch)

                    return true
                }
            }
            searchView.setOnQueryTextListener(queryTextListener)
        }
    }


    override fun onItemSelection(photo: FlickrPhoto) {
        if (album.photos.contains(photo)) {
            album.photos.remove(photo)
            Toast.makeText(context,getString(R.string.remove_from_album_text), Toast.LENGTH_LONG).show()
        } else {
            album.photos.add(photo)
            Toast.makeText(context,getString(R.string.add_to_album_text), Toast.LENGTH_LONG).show()
        }

        viewModel.updateAlbum(album)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search ->
                // Not implemented here
                return false
            else -> {
            }
        }
        searchView.setOnQueryTextListener(queryTextListener)
        return super.onOptionsItemSelected(item)
    }
}
