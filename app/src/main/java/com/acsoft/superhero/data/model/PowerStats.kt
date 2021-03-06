package com.acsoft.superhero.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class PowerStats(
    val intelligence:Int,
    val strength:Int,
    val speed:Int,
    val power:Int,
    val combat:Int) : Parcelable