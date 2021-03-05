package com.acsoft.superhero

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.acsoft.superhero.application.Resource
import com.acsoft.superhero.data.remote.RemoteHeroDataSource
import com.acsoft.superhero.data.remote.RetrofitClient
import com.acsoft.superhero.presentation.HeroModelFactory
import com.acsoft.superhero.presentation.HeroViewModel
import com.acsoft.superhero.repository.HeroRepositoryImpl
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<HeroViewModel> {
        HeroModelFactory(HeroRepositoryImpl(
            RemoteHeroDataSource(RetrofitClient.webService)
        ))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

            viewModel.getHerosApi().observe(this@MainActivity, {
                when (it) {
                    is Resource.Loading -> {
                        Log.d("NEW","Cargando...")
                    }
                    is Resource.Success -> {
                        Log.d("NEW",it.data.name)
                    }
                    is Resource.Failure -> {
                        Log.d("NEW","Fallo...")
                    }
                }

            })


    }
}