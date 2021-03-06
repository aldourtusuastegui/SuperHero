package com.acsoft.superhero.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Hero(
        val id: Int,
        val name: String,
        val image: Image,
        val powerstats: PowerStats,
        val biography : Biography
) : Parcelable

class HeroList(
        @SerializedName("results")
        val hero: List<Hero>
)