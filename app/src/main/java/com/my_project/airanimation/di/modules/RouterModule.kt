package com.my_project.airanimation.di.modules


import com.my_project.airanimation.router.Router
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created Максим on 11.10.2019.
 * Copyright © Max
 */
@Module
class RouterModule {

    @Provides
    @Singleton
    fun provideRouter() = Router()
}