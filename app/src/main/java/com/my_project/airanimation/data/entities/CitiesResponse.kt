package com.my_project.airanimation.data.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created Максим on 11.10.2019.
 * Copyright © Max
 */
@Parcelize
data class CitiesResponse (
    @SerializedName("cities")
    val cities: List<City>
):Parcelable
