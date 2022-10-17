package com.suzume.brewdogcatalogue.di

import com.suzume.brewdogcatalogue.App
import com.suzume.brewdogcatalogue.data.database.BeerDao
import com.suzume.brewdogcatalogue.data.database.RemoteKeyDao
import com.suzume.brewdogcatalogue.data.network.ApiFactory
import com.suzume.brewdogcatalogue.data.network.ApiService
import com.suzume.brewdogcatalogue.data.repository.BeerRepositoryImpl
import com.suzume.brewdogcatalogue.domain.repository.BeerRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindRepository(impl: BeerRepositoryImpl): BeerRepository

    companion object {

        @Provides
        fun provideBeerDao(): BeerDao {
            return App.getDatabase().beersDao()
        }

        @Provides
        fun provideRemoteKeyDao(): RemoteKeyDao {
            return App.getDatabase().remoteKeyDao()
        }

        @Provides
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }

    }

}