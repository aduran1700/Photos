package com.aduran.photos.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.aduran.photos.model.Album
import com.aduran.photos.model.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AlbumViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private var disposable = CompositeDisposable()
    private val album = MutableLiveData<Album>()

    fun getAblum(): LiveData<Album> {
        return album
    }

    fun getAlbum(albumId: Int) {
        disposable.add(repository.getAlbum(albumId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
            album.value = it
        }, {
        }))
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}
