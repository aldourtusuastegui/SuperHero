package com.acsoft.superhero.data.model

import com.google.gson.annotations.SerializedName

class Hero(
        val id: Int,
        val name: String,
        val image: Image
)

class HeroList(
        @SerializedName("results")
        val hero: List<Hero>
)