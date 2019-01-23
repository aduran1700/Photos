package com.aduran.photos.root.module

import com.aduran.photos.view.album.AlbumFragment
import com.aduran.photos.view.albumlist.AlbumListFragment
import com.aduran.photos.view.photolist.PhotoListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBindingModule {

    @ContributesAndroidInjector
    internal abstract fun providePhotoListFragment(): PhotoListFragment

    @ContributesAndroidInjector
    internal abstract fun provideAlbumListFragment(): AlbumListFragment

    @ContributesAndroidInjector
    internal abstract fun provideAlbumFragment(): AlbumFragment
}