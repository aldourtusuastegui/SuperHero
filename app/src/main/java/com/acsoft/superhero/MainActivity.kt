package com.acsoft.superhero

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.acsoft.superhero.application.AppConstants
import com.acsoft.superhero.core.Resource
import com.acsoft.superhero.data.model.Hero
import com.acsoft.superhero.data.remote.RemoteHeroDataSource
import com.acsoft.superhero.data.remote.RetrofitClient
import com.acsoft.superhero.databinding.ActivityMainBinding
import com.acsoft.superhero.presentation.HeroModelFactory
import com.acsoft.superhero.presentation.HeroViewModel
import com.acsoft.superhero.repository.HeroRepositoryImpl
import com.acsoft.superhero.ui.DetailActivity
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

        initRecycler()
        getHeros()
        onScroll()
    }

    private fun initRecycler() {
        adapter = HeroAdapter(this)
        layoutManager = GridLayoutManager(this,2)
        binding.rvHeros.layoutManager = layoutManager
        binding.rvHeros.adapter = adapter
    }

    private fun getHeros() {
        viewModel.getHerosApi().observe(this@MainActivity, { result ->
            when (result) {
                is Resource.Loading -> {
                    Log.d("TAG","Cargando...")
                }
                is Resource.Success -> {
                    adapter.setHero(result.data)
                    loading = false
                }
                is Resource.Failure -> {
                    loading = false
                }
            }

        })
    }

    private fun onScroll() {
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
    }


    override fun onHeroClick(hero: Hero) {
        val intent = Intent(this,DetailActivity::class.java)
        intent.putExtra(AppConstants.HERO,hero)
        startActivity(intent)
    }
}