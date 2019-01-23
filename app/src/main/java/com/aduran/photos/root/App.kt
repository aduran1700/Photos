package com.aduran.photos.root

import com.aduran.photos.root.component.ApplicationComponent
import com.aduran.photos.root.component.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class App : DaggerApplication () {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val component : ApplicationComponent = DaggerApplicationComponent.builder().application(this).build()
        component.inject(this)

        return component
    }
}