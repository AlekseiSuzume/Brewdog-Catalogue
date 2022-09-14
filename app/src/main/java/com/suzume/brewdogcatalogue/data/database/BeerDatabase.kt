package com.suzume.brewdogcatalogue.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BeerInfoDbModel::class], version = 1, exportSchema = false)
abstract class BeerDatabase : RoomDatabase() {
    abstract fun beersDao(): BeerDao
}