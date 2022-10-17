package com.suzume.brewdogcatalogue.domain.usecases

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.suzume.brewdogcatalogue.domain.entity.BeerInfoEntity
import com.suzume.brewdogcatalogue.domain.repository.BeerRepository
import javax.inject.Inject

class LoadDataUseCase @Inject constructor(private val repository: BeerRepository) {

    operator fun invoke(): LiveData<PagingData<BeerInfoEntity>> {
        return repository.loadData()
    }

}