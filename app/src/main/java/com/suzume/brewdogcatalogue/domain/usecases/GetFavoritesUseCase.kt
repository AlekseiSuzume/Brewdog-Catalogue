package com.suzume.brewdogcatalogue.domain.usecases

import androidx.lifecycle.LiveData
import com.suzume.brewdogcatalogue.domain.entity.BeerInfoEntity
import com.suzume.brewdogcatalogue.domain.repository.BeerRepository
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(private val repository: BeerRepository) {

    operator fun invoke(): LiveData<List<BeerInfoEntity>> {
        return repository.getFavoritesList()
    }

}