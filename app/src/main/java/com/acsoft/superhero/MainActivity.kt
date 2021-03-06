package com.acsoft.superhero

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var loading: Boolean = false


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

        layoutManager = GridLayoutManager(this,2)
        binding.rvHeros.layoutManager = layoutManager

        binding.rvHeros.adapter = adapter
        getHeros()

        binding.rvHeros.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(binding.rvHeros, dx, dy)
                val visibleItemCount = (layoutManager as GridLayoutManager).childCount
                val totalItemCount = (layoutManager as GridLayoutManager).itemCount
                val firstVisible = (layoutManager as GridLayoutManager).findFirstVisibleItemPosition()
                if (!loading && (visibleItemCount + firstVisible) >= totalItemCount) {
                    loading = true
                    viewModel.nextPage()
                    getHeros()
                }

            }
        })

        searchHeroByName()

    }

    private fun searchHeroByName() {
        binding.searchAccount.setOnQueryTextListener(object :  SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                getHeroByName(query!!)
                loading = true
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        binding.searchAccount.setOnCloseListener {
            getHeros()
            false
        }
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
                    loading = false
                }
                is Resource.Failure -> {
                    loading = false
                    Log.d("NEW","Fallo...")
                }
            }

        })
    }

    private fun getHeroByName(name:String) {
        viewModel.getHeroByName(name).observe(this@MainActivity, { result ->
            when (result) {
                is Resource.Loading -> {
                    Log.d("NEW","Cargando...")
                }
                is Resource.Success -> {
                    adapter.setHeroList(result.data.hero)
                    loading = true
                }
                is Resource.Failure -> {
                    loading = true
                    Log.d("NEW","Fallo...")
                }
            }

        })
    }

    override fun onHeroClick(hero: Hero) {
        Toast.makeText(this,hero.name,Toast.LENGTH_LONG).show()
    }
}