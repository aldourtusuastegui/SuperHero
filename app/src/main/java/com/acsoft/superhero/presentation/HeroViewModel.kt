package com.acsoft.superhero.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.acsoft.superhero.core.Resource
import com.acsoft.superhero.repository.HeroRepository
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class HeroViewModel(private val repository: HeroRepository): ViewModel() {

    private var firstItem = 1
    private var lastItem = 10

    fun nextPage() {
        firstItem = lastItem+1
        lastItem += 10
    }

    fun getHerosApi() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            for (i in firstItem..lastItem) {
                emit(Resource.Success(repository.getHerosApi(i)))
            }
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }



}

class HeroModelFactory(private val repo: HeroRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(HeroRepository::class.java).newInstance(repo)
    }
}