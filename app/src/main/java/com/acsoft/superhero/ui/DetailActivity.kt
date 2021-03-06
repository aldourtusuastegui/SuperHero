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
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .into(binding.ivHero)

        supportActionBar?.title = hero.name

        binding.pbIntelligence.progress = hero.powerstats.intelligence
        binding.pbStrength.progress = hero.powerstats.strength
        binding.pbSpeed.progress = hero.powerstats.speed
        binding.pbPower.progress = hero.powerstats.power
        binding.pbCombat.progress = hero.powerstats.combat

        binding.tvName.text = hero.name
        binding.tvFullname.text =
            if(!hero.biography.fullName.isNullOrEmpty()) hero.biography.fullName else "N/A"
        binding.tvFistAppearance.text = hero.biography.firstAppearance
        binding.tvPublisher.text = hero.biography.publisher
        binding.tvAlignment.text = hero.biography.alignment

    }
}