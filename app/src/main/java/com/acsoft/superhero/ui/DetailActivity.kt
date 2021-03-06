package com.acsoft.superhero.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.acsoft.superhero.R
import com.acsoft.superhero.application.AppConstants
import com.acsoft.superhero.data.model.Hero
import com.acsoft.superhero.databinding.ActivityDetailBinding
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private var hero: Hero? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        init()
    }

    private fun init() {
        hero = intent.getParcelableExtra(AppConstants.HERO)
        updateData(hero!!)
    }

    private fun updateData(hero: Hero) {

        Picasso.with(this)
            .load(hero.image.url)
            .into(binding.ivHero)

        supportActionBar?.title = hero.name

        binding.tvIntelligence.text = hero.powerstats.intelligence.toString().plus("%")
        binding.tvStrength.text = hero.powerstats.strength.toString().plus("%")
        binding.tvSpeed.text = hero.powerstats.speed.toString().plus("%")
        binding.tvPower.text = hero.powerstats.power.toString().plus("%")

        binding.tvName.text = hero.name
        binding.tvFistAppearance.text = hero.biography.firstAppearance
        binding.tvPublisher.text = hero.biography.publisher
        binding.tvAlignment.text = hero.biography.alignment

    }
}