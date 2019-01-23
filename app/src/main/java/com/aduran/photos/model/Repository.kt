package com.aduran.photos.model

import io.reactivex.Observable
import io.reactivex.Single

interface Repository {
    fun searchPhotos(term: String, isTag: Boolean = false) : Observable<FlickrResult>
    fun createAlbum(name: String)
    fun deleteAlbum(album: Album)
    fun getAlbums(): Single<List<Album>>
    fun getAlbum(albumId: Int): Single<Album>
    fun updateAlbum(album: Album)
}