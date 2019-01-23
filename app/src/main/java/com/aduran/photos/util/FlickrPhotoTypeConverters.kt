package com.aduran.photos.util

import android.arch.persistence.room.TypeConverter
import com.aduran.photos.model.FlickrPhoto
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson
import java.util.*


class FlickrPhotoTypeConverters {
    var gson = Gson()

    @TypeConverter
    fun stringToFlickrPhotoList(data: String?): List<FlickrPhoto> {
        if (data == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<List<FlickrPhoto>>() {

        }.type

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun flickrPhotoListToString(someObjects: List<FlickrPhoto>): String {
        return gson.toJson(someObjects)
    }
}