package com.aduran.photos.model


data class FlickrPhotos(val page: Int, val pages: Int, val perpage: Int, val total: Int, val photo: List<FlickrPhoto>)