package com.suzume.brewdogcatalogue.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [BeerInfoDbModel::class, RemoteKey::class], version = 1, exportSchema = false)
abstract class BeerDatabase : RoomDatabase() {
    abstract fun beersDao(): BeerDao
    abstract fun remoteKeyDao(): RemoteKeyDao

    companion object {
        private var INSTANCE: BeerDatabase? = null
        private val LOCK = Any()
        private const val DB_NAME = "shop_item.db"

        fun getInstance(application: Application): BeerDatabase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    application,
                    BeerDatabase::class.java,
                    DB_NAME
                ).build()
                INSTANCE = db
                return db
            }
        }
    }

}