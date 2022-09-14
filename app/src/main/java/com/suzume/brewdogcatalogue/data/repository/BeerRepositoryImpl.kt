package com.suzume.brewdogcatalogue.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.suzume.brewdogcatalogue.data.database.BeerDao
import com.suzume.brewdogcatalogue.data.mapper.Mapper
import com.suzume.brewdogcatalogue.data.network.ApiService
import com.suzume.brewdogcatalogue.domain.entity.BeerInfoEntity
import com.suzume.brewdogcatalogue.domain.repository.BeerRepository
import javax.inject.Inject

class BeerRepositoryImpl @Inject constructor(
    private val dao: BeerDao,
    private val apiService: ApiService,
    private val mapper: Mapper,
) : BeerRepository {

    override suspend fun loadData(page: Int) {
        try {
            val data = apiService.loadData(page = page)
            dao.insertBeerList(data.map { mapper.mapBeerInfoDtoToDbModel(it) })
        } catch (e: Exception) {
        }
    }

    override fun getBeerInfoList(): LiveData<List<BeerInfoEntity>> =
        Transformations.map(dao.getBeersList()) {
            it.map { mapper.mapBeerInfoDbModelToEntity(it) }
        }

    override fun getBeerInfo(id: Int): LiveData<BeerInfoEntity> =
        Transformations.map(dao.getBeerFromDb(id)) {
            mapper.mapBeerInfoDbModelToEntity(it)
        }

    override fun getFavoritesList(): LiveData<List<BeerInfoEntity>> {
        return Transformations.map(dao.getFavouritesBeerList()) {
            it.map { mapper.mapBeerInfoDbModelToEntity(it) }
        }
    }

    override suspend fun addFavorite(id: Int) {
        dao.favouriteOn(id)
    }

    override suspend fun removeFavorite(id: Int) {
        dao.favouriteOff(id)
        Log.d("MyTest", "$dao\n$apiService\n$this")
    }
}