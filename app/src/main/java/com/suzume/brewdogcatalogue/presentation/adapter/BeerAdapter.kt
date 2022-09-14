package com.suzume.brewdogcatalogue.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.suzume.brewdogcatalogue.databinding.ItemBeerBinding
import com.suzume.brewdogcatalogue.domain.entity.BeerInfoEntity

class BeerAdapter :
    ListAdapter<BeerInfoEntity, BeerViewHolder>(BeerDiffCallback()) {


    var onBeerClickListener: ((BeerInfoEntity) -> Unit)? = null
    var onReachEndListener: ((currentPosition: Int) -> Unit)? = null
    var onBeerLongClickListener: ((BeerInfoEntity) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBeerBinding.inflate(inflater, parent, false)
        return BeerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BeerViewHolder, position: Int) {
        val beer = getItem(position)
        holder.bind(beer)
        holder.itemView.setOnClickListener {
            onBeerClickListener?.invoke(beer)
        }
        holder.itemView.setOnLongClickListener {
            onBeerLongClickListener?.invoke(beer)
            true
        }
        if (position == currentList.size - 5) {
            onReachEndListener?.invoke(position)
        }
    }

}