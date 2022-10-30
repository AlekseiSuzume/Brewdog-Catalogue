package com.suzume.brewdogcatalogue.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.suzume.brewdogcatalogue.data.database.BeerDao
import com.suzume.brewdogcatalogue.data.database.BeerInfoDbModel
import com.suzume.brewdogcatalogue.data.database.RemoteKey
import com.suzume.brewdogcatalogue.data.database.RemoteKeyDao
import com.suzume.brewdogcatalogue.data.mapper.Mapper
import com.suzume.brewdogcatalogue.data.network.ApiService
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class MyRemoteMediator @Inject constructor(
    private val apiService: ApiService,
    private val beerDao: BeerDao,
    private val keysDao: RemoteKeyDao,
    private val mapper: Mapper,
) : RemoteMediator<Int, BeerInfoDbModel>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, BeerInfoDbModel>,
    ): MediatorResult {

        val page = when (val pageKeyData = getKeyPageData(loadType, state)) {
            is MediatorResult.Success -> {
                return pageKeyData
            }
            else -> {
                pageKeyData as Int
            }
        }

        return try {
            val beerInfoDbModels = fetchBeers(page)
            val endOfPaginationReached = beerInfoDbModels.isEmpty()
            val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
            val nextKey = if (endOfPaginationReached) null else page + 1
            val keys = beerInfoDbModels.map {
                RemoteKey(it.id, prevKey = prevKey, nextKey = nextKey)
            }
            keysDao.insertAll(keys)
            beerDao.insertBeerList(beerInfoDbModels)
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }

    }

    private suspend fun getKeyPageData(
        loadType: LoadType,
        state: PagingState<Int, BeerInfoDbModel>,
    ): Any {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }
            LoadType.APPEND -> {
                val remoteKeys = getLastRemoteKey(state)
                val nextKey = remoteKeys?.nextKey
                return nextKey ?: MediatorResult.Success(endOfPaginationReached = false)
            }
            LoadType.PREPEND -> {
                val remoteKeys = getFirstRemoteKey(state)
                val prevKey = remoteKeys?.prevKey ?: return MediatorResult.Success(
                    endOfPaginationReached = false
                )
                prevKey
            }
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, BeerInfoDbModel>): RemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                keysDao.remoteKeysRepoId(id)
            }
        }
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, BeerInfoDbModel>): RemoteKey? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { BeerInfoDbModel -> keysDao.remoteKeysRepoId(BeerInfoDbModel.id) }
    }

    private suspend fun getFirstRemoteKey(state: PagingState<Int, BeerInfoDbModel>): RemoteKey? {
        return state.pages
            .firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { BeerInfoDbModel -> keysDao.remoteKeysRepoId(BeerInfoDbModel.id) }
    }

    private suspend fun fetchBeers(page: Int): List<BeerInfoDbModel> {
        return apiService.loadData(page = page).map { mapper.mapBeerInfoDtoToDbModel(it) }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }

}