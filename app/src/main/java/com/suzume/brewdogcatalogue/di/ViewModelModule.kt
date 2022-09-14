package com.suzume.brewdogcatalogue.di

import androidx.lifecycle.ViewModel
import com.suzume.brewdogcatalogue.presentation.BeerInfoViewModel
import com.suzume.brewdogcatalogue.presentation.FavouriteViewModel
import com.suzume.brewdogcatalogue.presentation.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(MainViewModel::class)
    @Binds
    fun bindMainViewModule(impl: MainViewModel): ViewModel

    @IntoMap
    @ViewModelKey(BeerInfoViewModel::class)
    @Binds
    fun bindBeerInfoViewModel(impl: BeerInfoViewModel): ViewModel

    @IntoMap
    @ViewModelKey(FavouriteViewModel::class)
    @Binds
    fun bindFavouriteViewModel(impl: FavouriteViewModel): ViewModel

}