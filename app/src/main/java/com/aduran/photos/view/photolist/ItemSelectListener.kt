package com.aduran.photos.view.photolist

import com.aduran.photos.model.FlickrPhoto

interface ItemSelectListener {
    fun onItemSelection(photo: FlickrPhoto)
}