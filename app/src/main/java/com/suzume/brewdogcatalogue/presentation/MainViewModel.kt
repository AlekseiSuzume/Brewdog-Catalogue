package com.suzume.brewdogcatalogue.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suzume.brewdogcatalogue.domain.usecases.AddFavoriteUseCase
import com.suzume.brewdogcatalogue.domain.usecases.GetBeerInfoListUseCase
import com.suzume.brewdogcatalogue.domain.usecases.LoadDataUseCase
import com.suzume.brewdogcatalogue.domain.usecases.RemoveFavoriteUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor (
    private val loadDataUseCase: LoadDataUseCase,
    private val getBeerInfoListUseCase: GetBeerInfoListUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase,
) : ViewModel() {

    val beerList = getBeerInfoListUseCase()

    fun loadData(currentPosition: Int = START_POSITION) {
        val page = if (currentPosition == START_POSITION) {
            1
        } else {
            (currentPosition / ITEM_PER_PAGE) + 2
        }
        viewModelScope.launch {
            loadDataUseCase.invoke(page)
        }
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

    companion object {
        private const val START_POSITION = 0
        private const val ITEM_PER_PAGE = 50
    }

}