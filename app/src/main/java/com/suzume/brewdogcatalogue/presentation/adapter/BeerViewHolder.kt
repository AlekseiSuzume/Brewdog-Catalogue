package com.suzume.brewdogcatalogue.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.suzume.brewdogcatalogue.R
import com.suzume.brewdogcatalogue.databinding.ItemBeerBinding
import com.suzume.brewdogcatalogue.domain.entity.BeerInfoEntity

class BeerViewHolder(
    private val binding: ItemBeerBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(beerInfoEntity: BeerInfoEntity) {
        with(binding) {
            with(beerInfoEntity) {
                tvName.text = name
                tvTag.text = tagLine
                tvAbv.text = root.resources.getString(R.string.abv, abv)
                tvIbu.text = root.resources.getString(R.string.ibu, ibu)
                tvOg.text = targetOg
                if (imageUrl.isEmpty()) {
                    ivBeer.setImageResource(R.drawable.logo)
                } else {
                    Glide
                        .with(binding.root.context)
                        .load(imageUrl)
                        .into(ivBeer)
                }
                if (favourite) {
                    ivFavourite.setImageResource(R.drawable.beerglasson)
                } else {
                    ivFavourite.setImageResource(R.drawable.beerglassoff)
                }
            }
        }
    }
}