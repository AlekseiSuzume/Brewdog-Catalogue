package com.suzume.brewdogcatalogue.data.database

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*

@Dao
interface BeerDao {

    @Query("SELECT * FROM beers WHERE id = :id")
    fun getBeerFromDb(id: Int): LiveData<BeerInfoDbModel>

    @Query("SELECT * FROM beers WHERE favourite = 1")
    fun getFavouritesBeerList(): LiveData<List<BeerInfoDbModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBeerList(beerInfoDtoList: List<BeerInfoDbModel>)

    @Query("UPDATE beers SET favourite = 1 WHERE id = :id")
    suspend fun favouriteOn(id: Int)

    @Query("UPDATE beers SET favourite = 0 WHERE id = :id")
    suspend fun favouriteOff(id: Int)

    @Query("SELECT * FROM beers ORDER BY id")
    fun getPagingSource(): PagingSource<Int, BeerInfoDbModel>

    @Query("DELETE FROM beers")
    fun clear()

    @Transaction
    suspend fun refresh(beerInfoDtoList: List<BeerInfoDbModel>) {
        clear()
        insertBeerList(beerInfoDtoList)
    }
}