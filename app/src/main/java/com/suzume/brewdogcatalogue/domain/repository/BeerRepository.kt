package com.suzume.brewdogcatalogue.domain.repository

import androidx.lifecycle.LiveData
import com.suzume.brewdogcatalogue.domain.entity.BeerInfoEntity

interface BeerRepository {

    suspend fun loadData(page: Int)

    fun getBeerInfoList(): LiveData<List<BeerInfoEntity>>

    fun getBeerInfo(id: Int): LiveData<BeerInfoEntity>

    fun getFavoritesList(): LiveData<List<BeerInfoEntity>>

    suspend fun addFavorite(id: Int)

    suspend fun removeFavorite(id: Int)

}