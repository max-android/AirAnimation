package com.my_project.airanimation.data.repository

import com.my_project.airanimation.data.network.AirApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created Максим on 12.10.2019.
 * Copyright © Max
 */
class SearchRepository @Inject constructor(private val api: AirApiService) {

    fun cities(term:String) = api
        .citiesResponse(term)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}