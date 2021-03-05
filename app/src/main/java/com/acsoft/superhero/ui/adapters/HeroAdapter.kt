package com.acsoft.superhero.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.acsoft.superhero.core.BaseViewHolder
import com.acsoft.superhero.data.model.Hero
import com.acsoft.superhero.databinding.HeroItemBinding

class HeroAdapter(private val itemClickListener: OnHeroClickListener) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var heroList : MutableList<Hero> = mutableListOf()

    fun setHero(hero : Hero) {
        this.heroList.add(hero)
        notifyDataSetChanged()
    }

    interface OnHeroClickListener {
        fun onHeroClick(hero: Hero)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding =
            HeroItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val holder = HeroViewHolder(itemBinding)

        itemBinding.root.setOnClickListener {
            val position = holder.adapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                ?: return@setOnClickListener
            itemClickListener.onHeroClick(heroList[position])
        }

        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder) {
            is HeroViewHolder -> {
                holder.bind(heroList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return heroList.size
    }

    private inner class HeroViewHolder(val binding : HeroItemBinding) :
        BaseViewHolder<Hero>(binding.root) {
        override fun bind(item: Hero) {
            binding.tvTitle.text = item.name
        }
    }

}

