package com.suzume.brewdogcatalogue.domain.usecases

import com.suzume.brewdogcatalogue.domain.repository.BeerRepository
import javax.inject.Inject

class AddFavoriteUseCase @Inject constructor(private val repository: BeerRepository) {

    suspend operator fun invoke(id: Int) {
        repository.addFavorite(id)
    }

}