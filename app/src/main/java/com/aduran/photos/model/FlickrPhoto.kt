package com.aduran.photos.model

data class FlickrPhoto (val id: String, val secret :String, val server: String, val farm: String) {
    fun url() = "https://farm$farm.staticflickr.com/$server/${id}_$secret.jpg"
}