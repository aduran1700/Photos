package com.aduran.photos.model

import android.arch.persistence.room.*
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface AlbumDao {
    @Query("SELECT * FROM Album")
    fun getAll(): Single<List<Album>>

    @Query("SELECT * FROM Album WHERE id = :albumId")
    fun getAlbum(albumId: Int): Single<Album>

    @Insert
    fun insert(album: Album)

    @Update
    fun update(album: Album)

    @Delete
    fun delete(album: Album)
}