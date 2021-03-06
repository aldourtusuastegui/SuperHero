package com.acsoft.superhero.repository

import com.acsoft.superhero.data.model.Hero

interface HeroRepository {

    suspend fun getHerosApi(id:Int) :Hero

}