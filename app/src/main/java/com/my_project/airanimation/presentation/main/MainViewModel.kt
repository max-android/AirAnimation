package com.my_project.airanimation.presentation.main

import androidx.lifecycle.ViewModel
import com.my_project.airanimation.App
import com.my_project.airanimation.data.repository.MainRepository
import com.my_project.airanimation.router.Router
import com.my_project.airanimation.router.Screen
import javax.inject.Inject

/**
 * Created Максим on 11.10.2019.
 * Copyright © Max
 */
class MainViewModel: ViewModel() {

    @Inject
    lateinit var mRepository:MainRepository
    @Inject
    lateinit var router: Router

    init {
        App.appComponent.inject(this)
    }

    fun showParenthetical() = router.replace(Screen.INTRODUCTION)

    fun back() = router.back()

}