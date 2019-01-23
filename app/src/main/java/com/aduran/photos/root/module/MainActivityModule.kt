package com.aduran.photos.root.module

import com.aduran.photos.view.main.MainActivity
import dagger.Binds
import dagger.Module

@Module
abstract class MainActivityModule {

    @Binds
    internal abstract fun provideMainActivity(activity: MainActivity): MainActivity
}