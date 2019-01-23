package com.aduran.photos.view.albumlist

import com.aduran.photos.model.Album

interface ItemSelectListener {
    fun onItemSelection(album: Album)
}