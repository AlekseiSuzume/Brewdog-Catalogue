package com.suzume.brewdogcatalogue.domain.usecases

import com.suzume.brewdogcatalogue.domain.repository.BeerRepository
import javax.inject.Inject

class LoadDataUseCase @Inject constructor(private val repository: BeerRepository) {

    suspend operator fun invoke(page: Int) {
        repository.loadData(page)
    }

}