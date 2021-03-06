package com.acsoft.superhero.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Biography(
    @SerializedName("first-appearance")
    val firstAppearance:String,
    val publisher:String,
    val alignment:String
) :Parcelable