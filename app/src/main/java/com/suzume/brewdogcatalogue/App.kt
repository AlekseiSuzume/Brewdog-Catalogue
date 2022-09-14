package com.suzume.brewdogcatalogue

import android.app.Application
import androidx.room.Room
import com.suzume.brewdogcatalogue.data.database.BeerDatabase
import com.suzume.brewdogcatalogue.di.DaggerApplicationComponent

class App : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        db = Room
            .databaseBuilder(applicationContext, BeerDatabase::class.java, DB_NAME)
            .build()
    }

    companion object {
        private lateinit var db: BeerDatabase
        private const val DB_NAME = "beers.db"
        fun getDatabase(): BeerDatabase {
            return db
        }
    }

}
