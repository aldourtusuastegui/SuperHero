package com.acsoft.superhero.repository

import com.acsoft.superhero.data.model.Hero
import com.acsoft.superhero.data.remote.RemoteHeroDataSource

class HeroRepositoryImpl(private val dataSourceRemote : RemoteHeroDataSource) : HeroRepository {

    override suspend fun getHerosApi(id:Int): Hero {
        return dataSourceRemote.getHeros(id)
    }

}