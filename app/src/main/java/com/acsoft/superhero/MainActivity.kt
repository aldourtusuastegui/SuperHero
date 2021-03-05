package com.acsoft.superhero

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.acsoft.superhero.core.Resource
import com.acsoft.superhero.data.model.Hero
import com.acsoft.superhero.data.remote.RemoteHeroDataSource
import com.acsoft.superhero.data.remote.RetrofitClient
import com.acsoft.superhero.databinding.ActivityMainBinding
import com.acsoft.superhero.presentation.HeroModelFactory
import com.acsoft.superhero.presentation.HeroViewModel
import com.acsoft.superhero.repository.HeroRepositoryImpl
import com.acsoft.superhero.ui.adapters.HeroAdapter

class MainActivity : AppCompatActivity(),HeroAdapter.OnHeroClickListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: HeroAdapter

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

        adapter = HeroAdapter(this)
        binding.rvHeros.layoutManager = LinearLayoutManager(this)
        binding.rvHeros.adapter = adapter


        getHeros()


       /* binding.button.setOnClickListener {
            viewModel.nextPage()
            getHeros()
        }*/

    }

    private fun getHeros() {
        viewModel.getHerosApi().observe(this@MainActivity, { result ->
            when (result) {
                is Resource.Loading -> {
                    Log.d("NEW","Cargando...")
                }
                is Resource.Success -> {
                    adapter.setHero(result.data)
                    Log.d("NEW",result.data.name)
                }
                is Resource.Failure -> {
                    Log.d("NEW","Fallo...")
                }
            }

        })
    }

    override fun onHeroClick(hero: Hero) {
        Toast.makeText(this,hero.name,Toast.LENGTH_LONG).show()
    }
}