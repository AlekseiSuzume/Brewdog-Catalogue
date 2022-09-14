package com.suzume.brewdogcatalogue.domain.usecases

import androidx.lifecycle.LiveData
import com.suzume.brewdogcatalogue.domain.entity.BeerInfoEntity
import com.suzume.brewdogcatalogue.domain.repository.BeerRepository
import javax.inject.Inject

class GetBeerInfoUseCase @Inject constructor(private val repository: BeerRepository) {

    operator fun invoke(id: Int): LiveData<BeerInfoEntity> {
        return repository.getBeerInfo(id)
    }

}