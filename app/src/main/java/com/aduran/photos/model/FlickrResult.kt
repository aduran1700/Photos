package com.aduran.photos.model

import com.google.gson.annotations.SerializedName

data class FlickrResult(@SerializedName("photos") var photos: FlickrPhotos)