package com.suzume.brewdogcatalogue.domain.usecases

import com.suzume.brewdogcatalogue.domain.repository.BeerRepository
import javax.inject.Inject

class RemoveFavoriteUseCase @Inject constructor(private val repository: BeerRepository) {

    suspend operator fun invoke(id: Int) {
        repository.removeFavorite(id)
    }

}