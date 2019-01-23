package com.aduran.photos.root.component.module

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module

@Module
abstract class ContextModule {

    @Binds
    internal abstract fun provideContext(application: Application): Context
}