package com.aduran.photos.model

import com.aduran.photos.network.FlickrApiService
import io.reactivex.Observable
import io.reactivex.Single

class PhotoRepository(private val service: FlickrApiService, private val albumDao: AlbumDao) : Repository {

    override fun searchPhotos(term: String, isTag: Boolean): Observable<FlickrResult> {
        val map: MutableMap<String, String> = mutableMapOf()

        if(isTag) {
            map["tags"] = term
        } else {
            map["text"] = term
        }
        return service.getPhotos(map)
    }

    override fun createAlbum(name: String) {
        albumDao.insert(Album(title = name))
    }

    override fun deleteAlbum(album: Album) {
        albumDao.delete(album)
    }

    override fun getAlbums(): Single<List<Album>> {
        return albumDao.getAll()
    }

    override fun updateAlbum(album: Album) {
        albumDao.update(album)
    }

    override fun getAlbum(albumId: Int): Single<Album> {
        return albumDao.getAlbum(albumId)
    }
}