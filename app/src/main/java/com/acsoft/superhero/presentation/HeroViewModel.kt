package com.acsoft.superhero.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.acsoft.superhero.application.Resource
import com.acsoft.superhero.repository.HeroRepository
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class HeroViewModel(private val repository: HeroRepository): ViewModel() {

    fun getHerosApi() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            for (i in 1..10) {
                emit(Resource.Success(repository.getHerosApi(i)))
                Log.d("NEW",i.toString())
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