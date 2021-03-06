package com.acsoft.superhero.data.remote

import com.acsoft.superhero.data.model.Hero
import com.acsoft.superhero.data.model.HeroList

class RemoteHeroDataSource(private val webService: WebService) {

    suspend fun getHeros(id:Int) : Hero {
        return webService.getHeros(id)
    }

    suspend fun getHeroByName(name:String) : HeroList {
        return webService.getHeroByName(name)
    }

}