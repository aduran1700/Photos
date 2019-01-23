package com.aduran.photos.view.photolist

import android.databinding.DataBindingUtil
import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.aduran.photos.R
import com.aduran.photos.databinding.FragmentPhotoListItemBinding
import com.aduran.photos.model.FlickrPhoto

class PhotoRecyclerViewAdapter(private val listener: ItemSelectListener) : RecyclerView.Adapter<PhotoRecyclerViewAdapter.ViewHolder>() {

    private val photos = mutableListOf<FlickrPhoto>()

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val inflater  = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<FragmentPhotoListItemBinding>(
            inflater, R.layout.fragment_photo_list_item, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = photos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo = photos[position]
        holder.bind(photo)
    }

    fun updateList(updates: List<FlickrPhoto>) {
        this.photos.clear()
        this.photos.addAll(updates)
        notifyDataSetChanged()
    }


    inner class ViewHolder(private val binding: FragmentPhotoListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: FlickrPhoto) {
            binding.photo = photo
            ViewCompat.setTransitionName(binding.imageView, photo.url())

            binding.root.setOnClickListener{listener.onItemSelection(photo)}
            binding.executePendingBindings()
        }
    }
}