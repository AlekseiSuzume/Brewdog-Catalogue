package com.suzume.brewdogcatalogue.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.map
import androidx.paging.*
import com.suzume.brewdogcatalogue.data.MyRemoteMediator
import com.suzume.brewdogcatalogue.data.MyRemoteMediator3
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
    private val myRemoteMediator: MyRemoteMediator,
) : BeerRepository {

    override fun loadData(): LiveData<PagingData<BeerInfoEntity>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            PagingConfig(pageSize = ITEM_PER_PAGE, initialLoadSize = 1),
            remoteMediator = myRemoteMediator
        ) {
            dao.getPagingSource()
        }.liveData.map { pagingData ->
            pagingData.map { beerInfoDbModel ->
                mapper.mapBeerInfoDbModelToEntity(beerInfoDbModel)
            }
        }
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

    companion object {
        private const val ITEM_PER_PAGE = 50
    }
}