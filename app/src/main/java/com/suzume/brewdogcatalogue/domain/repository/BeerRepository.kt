package com.suzume.brewdogcatalogue.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.suzume.brewdogcatalogue.domain.entity.BeerInfoEntity

interface BeerRepository {


    fun loadData(): LiveData<PagingData<BeerInfoEntity>>

    fun getBeerInfo(id: Int): LiveData<BeerInfoEntity>

    fun getFavoritesList(): LiveData<List<BeerInfoEntity>>

    suspend fun addFavorite(id: Int)

    suspend fun removeFavorite(id: Int)

}