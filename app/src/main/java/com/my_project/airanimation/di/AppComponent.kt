package com.my_project.airanimation.di

import com.my_project.airanimation.di.modules.NetworkModule
import com.my_project.airanimation.di.modules.ProviderModule
import com.my_project.airanimation.di.modules.RepositoryModule
import com.my_project.airanimation.di.modules.RouterModule
import com.my_project.airanimation.presentation.introduction.IntroductionViewModel
import com.my_project.airanimation.presentation.main.MainActivity
import com.my_project.airanimation.presentation.main.MainViewModel
import com.my_project.airanimation.presentation.map.MapViewModel
import com.my_project.airanimation.presentation.search.SearchViewModel
import dagger.Component
import javax.inject.Singleton

/**
 * Created Максим on 11.10.2019.
 * Copyright © Max
 */
@Singleton
@Component
    (
    modules = [
        NetworkModule::class,
        RepositoryModule::class,
        ProviderModule::class,
        RouterModule::class
    ]
)
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(mainViewModel: MainViewModel)
    fun inject(introductionViewModel: IntroductionViewModel)
    fun inject(searchViewModel: SearchViewModel)
    fun inject(mapViewModel: MapViewModel)

}