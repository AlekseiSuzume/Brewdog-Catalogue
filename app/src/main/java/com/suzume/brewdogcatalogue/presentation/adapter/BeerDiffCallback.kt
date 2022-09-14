package com.suzume.brewdogcatalogue.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.suzume.brewdogcatalogue.domain.entity.BeerInfoEntity

class BeerDiffCallback: DiffUtil.ItemCallback<BeerInfoEntity>() {
    override fun areItemsTheSame(oldItem: BeerInfoEntity, newItem: BeerInfoEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: BeerInfoEntity, newItem: BeerInfoEntity): Boolean {
        return oldItem == newItem
    }
}