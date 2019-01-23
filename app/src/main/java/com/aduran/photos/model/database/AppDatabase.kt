package com.aduran.photos.model.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.aduran.photos.model.Album
import com.aduran.photos.model.AlbumDao
import com.aduran.photos.util.FlickrPhotoTypeConverters

@TypeConverters(value = [FlickrPhotoTypeConverters::class])
@Database(entities = [Album::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun AlbumDao(): AlbumDao
}