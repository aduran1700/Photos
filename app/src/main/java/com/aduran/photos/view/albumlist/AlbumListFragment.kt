package com.aduran.photos.view.albumlist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.aduran.photos.R
import com.aduran.photos.model.Album
import com.aduran.photos.view.album.AlbumFragment
import com.aduran.photos.viewmodel.AlbumListViewModel
import com.aduran.photos.viewmodel.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class AlbumListFragment : DaggerFragment(), ItemSelectListener {
    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory<AlbumListViewModel>
    private lateinit var viewModel: AlbumListViewModel
    private lateinit var listView: RecyclerView
    private lateinit var fab: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.album_list_fragment, container, false)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = getString(R.string.fragment_album_list_title)

        fab = view.findViewById(R.id.fab)
        listView = view.findViewById(R.id.list)
        listView.layoutManager = LinearLayoutManager(activity!!.applicationContext)
        listView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        viewModel = ViewModelProviders.of(this,viewModelFactory).get(AlbumListViewModel::class.java)
        val adapter = AlbumRecyclerViewAdapter(this)
        listView.adapter = adapter

        viewModel.getAlbums().observe(this, Observer<List<Album>> {
            if (it != null) {
                listView.visibility = View.VISIBLE
                adapter.updateList(it)
            }

        })

        fab.setOnClickListener {
            val layoutInflaterAndroid = LayoutInflater.from(context)
            val mView = layoutInflaterAndroid.inflate(R.layout.input_dialog, null)
            val alertDialogBuilderUserInput = AlertDialog.Builder(context!!)
            alertDialogBuilderUserInput.setView(mView)

            val userInputDialogEditText = mView.findViewById(R.id.userInputDialog) as EditText
            alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("Save") { dialogBox, id ->
                    viewModel.addAlbum(userInputDialogEditText.text.toString())
                }

                .setNegativeButton(
                    "Cancel"
                ) { dialogBox, _ -> dialogBox.cancel() }

            val alertDialogAndroid = alertDialogBuilderUserInput.create()
            alertDialogAndroid.show()
        }


        return view
    }

    override fun onItemSelection(album: Album) {
        activity!!.supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, AlbumFragment.newInstance(album.id))
            .addToBackStack(album.title)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }
}
