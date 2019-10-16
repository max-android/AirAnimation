package com.my_project.airanimation.di.modules

import com.my_project.airanimation.data.network.AirApiService
import com.my_project.airanimation.data.repository.IntroductionRepository
import com.my_project.airanimation.data.repository.MainRepository
import com.my_project.airanimation.data.repository.MapRepository
import com.my_project.airanimation.data.repository.SearchRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created Максим on 11.10.2019.
 * Copyright © Max
 */
@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideMainRepository() = MainRepository()

    @Provides
    @Singleton
    fun provideSearchRepository(api: AirApiService) = SearchRepository(api)

    @Provides
    @Singleton
    fun provideIntroductionRepository() = IntroductionRepository()

    @Provides
    @Singleton
    fun provideMapRepository() = MapRepository()

}