package com.aduran.photos.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.aduran.photos.model.Album
import com.aduran.photos.model.Repository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AlbumListViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private var disposable = CompositeDisposable()
    private val albums = MutableLiveData<List<Album>>()

    init {
        loadAlbums()
    }

    fun getAlbums(): LiveData<List<Album>> {
        return albums
    }

    fun addAlbum(name: String) {
        disposable.add(Single.fromCallable { repository.createAlbum(name) }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
           loadAlbums()
        }, {
        }))

    }


    fun loadAlbums() {
       disposable.add(repository.getAlbums().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
               albums.value = it
           }, {
           }))
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}
