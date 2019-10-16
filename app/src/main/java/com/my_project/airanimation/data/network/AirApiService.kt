package com.my_project.airanimation.data.network

import com.my_project.airanimation.data.entities.CitiesResponse
import com.my_project.airanimation.utilities.AUTOCOMPLETE
import com.my_project.airanimation.utilities.LANG
import com.my_project.airanimation.utilities.RU
import com.my_project.airanimation.utilities.TERM
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created Максим on 11.10.2019.
 * Copyright © Max
 */
interface AirApiService {
    @GET(AUTOCOMPLETE)
    fun citiesResponse(
    @Query(TERM) term: String,
    @Query(LANG) lang: String = RU
): Single<CitiesResponse>

}