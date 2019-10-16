package com.my_project.airanimation.data.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created Максим on 11.10.2019.
 * Copyright © Max
 */
@Parcelize
data class City (
    @SerializedName("countryCode") val code: String,
    @SerializedName("latinFullName") val fullName: String,
    @SerializedName("location") val loc: Loc,
    @SerializedName("latinCity") val town: String,
    @SerializedName("id") val id: Int,
    @SerializedName("iata") val iata: List<String>
): Parcelable
