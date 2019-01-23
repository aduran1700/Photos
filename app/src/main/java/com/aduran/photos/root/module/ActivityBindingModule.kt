package com.aduran.photos.root.module

import com.aduran.photos.view.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [MainActivityModule::class, MainFragmentBindingModule::class])
    internal abstract fun bindMainActivity(): MainActivity
}