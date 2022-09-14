package com.suzume.brewdogcatalogue.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suzume.brewdogcatalogue.domain.usecases.AddFavoriteUseCase
import com.suzume.brewdogcatalogue.domain.usecases.GetFavoritesUseCase
import com.suzume.brewdogcatalogue.domain.usecases.RemoveFavoriteUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouriteViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase,
) : ViewModel() {

    val favorites = getFavoritesUseCase()

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