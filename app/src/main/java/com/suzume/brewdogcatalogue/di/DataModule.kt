package com.suzume.brewdogcatalogue.di

import android.app.Application
import com.suzume.brewdogcatalogue.data.database.BeerDao
import com.suzume.brewdogcatalogue.data.database.BeerDatabase
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

        @ApplicationScope
        @Provides
        fun provideBeerDao(application: Application): BeerDao {
            return BeerDatabase.getInstance(application).beersDao()
        }

        @ApplicationScope
        @Provides
        fun provideRemoteKeyDao(application: Application): RemoteKeyDao {
            return BeerDatabase.getInstance(application).remoteKeyDao()
        }

        @ApplicationScope
        @Provides
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }

    }

}