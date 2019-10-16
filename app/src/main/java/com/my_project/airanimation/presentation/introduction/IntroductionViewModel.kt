package com.my_project.airanimation.presentation.introduction

import androidx.lifecycle.ViewModel
import com.my_project.airanimation.App
import com.my_project.airanimation.data.entities.City
import com.my_project.airanimation.data.entities.PairCity
import com.my_project.airanimation.data.repository.IntroductionRepository
import com.my_project.airanimation.router.Router
import com.my_project.airanimation.router.Screen
import javax.inject.Inject

/**
 * Created Максим on 11.10.2019.
 * Copyright © Max
 */
class IntroductionViewModel : ViewModel() {

    @Inject
    lateinit var iRepository: IntroductionRepository
    @Inject
    lateinit var router: Router
    var fromCity: City? = null
    var toCity: City? = null

    init {
        App.appComponent.inject(this)
    }

    fun showMap(pairCities: PairCity) = router.forward(Screen.MAP, pairCities)

}