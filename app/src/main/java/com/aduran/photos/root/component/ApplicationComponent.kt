package com.aduran.photos.root.component

import android.app.Application
import com.aduran.photos.model.AlbumDao
import com.aduran.photos.model.database.AppDatabase
import com.aduran.photos.root.App
import com.aduran.photos.root.component.module.ContextModule
import com.aduran.photos.root.module.ActivityBindingModule
import com.aduran.photos.root.module.ApplicationModule
import com.aduran.photos.root.module.FlickrApiModule
import com.aduran.photos.root.module.MainActivityModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication
import javax.inject.Singleton

@Singleton
@Component(modules = [ContextModule::class, MainActivityModule::class, AndroidSupportInjectionModule::class, ApplicationModule::class, FlickrApiModule::class, ActivityBindingModule::class]
)
interface ApplicationComponent : AndroidInjector<DaggerApplication> {

    fun inject(application: App)
    fun albumDao(): AlbumDao
    fun appDatabase(): AppDatabase


    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}