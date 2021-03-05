package com.acsoft.superhero

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.acsoft.superhero.core.Resource
import com.acsoft.superhero.data.remote.RemoteHeroDataSource
import com.acsoft.superhero.data.remote.RetrofitClient
import com.acsoft.superhero.databinding.ActivityMainBinding
import com.acsoft.superhero.presentation.HeroModelFactory
import com.acsoft.superhero.presentation.HeroViewModel
import com.acsoft.superhero.repository.HeroRepositoryImpl

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<HeroViewModel> {
        HeroModelFactory(HeroRepositoryImpl(
            RemoteHeroDataSource(RetrofitClient.webService)
        ))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        getHeros()


        binding.button.setOnClickListener {
            viewModel.nextPage()
            getHeros()
        }

    }

    private fun getHeros() {
        viewModel.getHerosApi().observe(this@MainActivity, { result ->
            when (result) {
                is Resource.Loading -> {
                    Log.d("NEW","Cargando...")
                }
                is Resource.Success -> {
                    Log.d("NEW",result.data.name)
                }
                is Resource.Failure -> {
                    Log.d("NEW","Fallo...")
                }
            }

        })
    }
}