package com.suzume.brewdogcatalogue.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "beers")
data class BeerInfoDbModel(
    @PrimaryKey
    val id: Int,
    var favourite: Boolean,
    val name: String,
    val tagLine: String,
    val description: String,
    val imageUrl: String,
    val abv: String,
    val ibu: String,
    val targetOg: String,
    val malts: String,
    val hops: String,
    val yeast: String

)
