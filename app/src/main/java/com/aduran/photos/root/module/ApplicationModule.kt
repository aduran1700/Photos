package com.aduran.photos.root.module

import android.arch.persistence.room.Room
import android.content.Context
import com.aduran.photos.model.AlbumDao
import com.aduran.photos.model.PhotoRepository
import com.aduran.photos.model.Repository
import com.aduran.photos.model.database.AppDatabase
import com.aduran.photos.network.FlickrApiService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Singleton
    @Provides
    fun provideRepository(service: FlickrApiService, albumDao: AlbumDao) : Repository {
        return PhotoRepository(service, albumDao)
    }

    @Singleton
    @Provides
    fun provideDatabase(context: Context) : AppDatabase {
        return Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "album-database").build()
    }

    @Provides
    fun provideAlbumDao(appDatabase: AppDatabase): AlbumDao {
        return appDatabase.AlbumDao()
    }
}
