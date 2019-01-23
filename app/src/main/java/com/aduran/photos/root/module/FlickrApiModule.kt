package com.aduran.photos.root.module

import com.aduran.photos.network.FlickrApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.flickr.com/"
const val METHOD = "flickr.photos.search"
const val API_KEY = "Your_Key_Here"
const val FORMAT = "json"
const val NO_CALLBACK = "1"

@Module
class FlickrApiModule {


    @Provides
    fun provideClient(): OkHttpClient {
        val clientBuilder = OkHttpClient().newBuilder()
        clientBuilder.addInterceptor {

            val original = it.request()
            val originalHttpUrl = original.url()

            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("method", METHOD)
                .addQueryParameter("api_key", API_KEY)
                .addQueryParameter("format", FORMAT)
                .addQueryParameter("nojsoncallback", NO_CALLBACK)
                .build()

            val requestBuilder = original.newBuilder()
                .url(url)

            val request = requestBuilder.build()
            it.proceed(request)
        }

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        clientBuilder.addInterceptor(loggingInterceptor)

        return clientBuilder.build()
    }

    @Provides
    fun provideRetroFit(baseUrl: String, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideApiService(): FlickrApiService {
        return provideRetroFit(BASE_URL, provideClient()).create(FlickrApiService::class.java)
    }
}