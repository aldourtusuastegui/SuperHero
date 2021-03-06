package com.acsoft.superhero.repository

import com.acsoft.superhero.data.model.Hero
import com.acsoft.superhero.data.model.HeroList

interface HeroRepository {

    suspend fun getHerosApi(id:Int) :Hero

    suspend fun getHeroByName(name:String) : HeroList

}