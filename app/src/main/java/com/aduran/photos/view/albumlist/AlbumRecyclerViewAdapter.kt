package com.aduran.photos.view.albumlist

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.aduran.photos.R
import com.aduran.photos.databinding.FragmentAlbumListItemBinding
import com.aduran.photos.model.Album

class AlbumRecyclerViewAdapter(private val listener: ItemSelectListener) : RecyclerView.Adapter<AlbumRecyclerViewAdapter.ViewHolder>() {

    private val albums = mutableListOf<Album>()

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val inflater  = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<FragmentAlbumListItemBinding>(
            inflater, R.layout.fragment_album_list_item, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = albums.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val album = albums[position]
        holder.bind(album)
    }

    fun updateList(updates: List<Album>) {
        this.albums.clear()
        this.albums.addAll(updates)
        notifyDataSetChanged()
    }


    inner class ViewHolder(private val binding: FragmentAlbumListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(album: Album) {
            binding.album = album
            binding.root.setOnClickListener{listener.onItemSelection(album)}
            binding.executePendingBindings()
        }
    }
}