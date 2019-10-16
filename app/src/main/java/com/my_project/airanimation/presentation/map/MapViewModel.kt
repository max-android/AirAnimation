package com.my_project.airanimation.presentation.map

import androidx.lifecycle.ViewModel
import com.my_project.airanimation.App
import com.my_project.airanimation.data.repository.MapRepository
import javax.inject.Inject

/**
 * Created Максим on 11.10.2019.
 * Copyright © Max
 */
class MapViewModel:ViewModel(){

    @Inject lateinit var mRepository: MapRepository

    init {
        App.appComponent.inject(this)
    }
}

