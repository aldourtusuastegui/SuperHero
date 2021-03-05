package com.acsoft.superhero.data.remote

import com.acsoft.superhero.data.model.Hero

class RemoteHeroDataSource(private val webService: WebService) {

    suspend fun getHeros(id:Int) : Hero {
        return webService.getHeros(id)
    }

}