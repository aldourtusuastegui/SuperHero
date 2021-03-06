package com.acsoft.superhero.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Hero(
        val name: String,
        val image: Image,
        val powerstats: PowerStats,
        val biography : Biography
) : Parcelable
