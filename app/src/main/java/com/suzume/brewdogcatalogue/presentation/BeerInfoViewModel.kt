package com.suzume.brewdogcatalogue.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suzume.brewdogcatalogue.domain.entity.BeerInfoEntity
import com.suzume.brewdogcatalogue.domain.usecases.AddFavoriteUseCase
import com.suzume.brewdogcatalogue.domain.usecases.GetBeerInfoUseCase
import com.suzume.brewdogcatalogue.domain.usecases.RemoveFavoriteUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class BeerInfoViewModel @Inject constructor (
    private val getBeerInfoUseCase: GetBeerInfoUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase,
) : ViewModel() {


    fun getBeerInfo(id: Int): LiveData<BeerInfoEntity> {
        return getBeerInfoUseCase(id)
    }

    fun addFavorite(id: Int) {
        viewModelScope.launch {
            addFavoriteUseCase.invoke(id)
        }
    }

    fun removeFavorite(id: Int) {
        viewModelScope.launch {
            removeFavoriteUseCase.invoke(id)
        }
    }

}