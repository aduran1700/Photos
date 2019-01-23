package com.aduran.photos.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.aduran.photos.model.Album
import com.aduran.photos.model.FlickrPhoto
import com.aduran.photos.model.Repository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PhotoListViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private var disposable = CompositeDisposable()
    private val photos = MutableLiveData<List<FlickrPhoto>>()

    fun getPhotos(): LiveData<List<FlickrPhoto>> {
        return photos
    }

    fun updateAlbum(album: Album) {
        disposable.add(Single.fromCallable { repository.updateAlbum(album) }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe())
    }


    fun searchPhotos(term: String, isTagSearch: Boolean) {
        disposable.add(repository.searchPhotos(term, isTagSearch).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
            photos.value = it.photos.photo
        }, {
        }))
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}
