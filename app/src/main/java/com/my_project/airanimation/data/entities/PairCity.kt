package com.my_project.airanimation.data.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created Максим on 12.10.2019.
 * Copyright © Max
 */
@Parcelize
class PairCity (val fromCity:City,val toCity:City): Parcelable
