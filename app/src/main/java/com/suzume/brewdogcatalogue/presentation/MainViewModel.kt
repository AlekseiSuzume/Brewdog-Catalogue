package com.suzume.brewdogcatalogue.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.suzume.brewdogcatalogue.domain.usecases.AddFavoriteUseCase
import com.suzume.brewdogcatalogue.domain.usecases.LoadDataUseCase
import com.suzume.brewdogcatalogue.domain.usecases.RemoveFavoriteUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase,
    private val loadDataUseCase: LoadDataUseCase,
) : ViewModel() {

    val beerList = loadDataUseCase().cachedIn(viewModelScope)

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