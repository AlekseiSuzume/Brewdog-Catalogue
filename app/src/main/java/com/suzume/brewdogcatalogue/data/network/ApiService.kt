package com.suzume.brewdogcatalogue.data.network

import com.suzume.brewdogcatalogue.data.network.model.BeerInfoDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    private companion object {
        const val QUERY_PARAMS_PAGE = "page"
        const val QUERY_PARAMS_PER_PAGE = "per_page"

    }

    @GET("beers")
    suspend fun loadData(
        @Query(QUERY_PARAMS_PAGE) page: Int = 1,
        @Query(QUERY_PARAMS_PER_PAGE) countPerPage: Int = 50
    ): List<BeerInfoDto>
}