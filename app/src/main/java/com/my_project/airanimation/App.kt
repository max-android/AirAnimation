package com.my_project.airanimation

import android.app.Application
import com.my_project.airanimation.di.AppComponent
import com.my_project.airanimation.di.DaggerAppComponent
import com.my_project.airanimation.di.modules.NetworkModule
import com.my_project.airanimation.di.modules.ProviderModule
import com.my_project.airanimation.di.modules.RepositoryModule
import com.my_project.airanimation.di.modules.RouterModule
import timber.log.Timber

/**
 * Created Максим on 11.10.2019.
 * Copyright © Max
 */
class App : Application() {

    companion object {
        @JvmStatic
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        initComponent()
        initTimber()
    }

    private fun initComponent() {
        appComponent = DaggerAppComponent.builder()
            .routerModule(RouterModule())
            .networkModule(NetworkModule())
            .repositoryModule(RepositoryModule())
            .providerModule(ProviderModule(this))
            .build()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}
