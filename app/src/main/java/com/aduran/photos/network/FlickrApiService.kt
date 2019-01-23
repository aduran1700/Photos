package com.aduran.photos.network

import com.aduran.photos.model.FlickrResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface FlickrApiService {

    @GET("/services/rest")
    fun getPhotos(@QueryMap params: Map<String, String>) : Observable<FlickrResult>
}