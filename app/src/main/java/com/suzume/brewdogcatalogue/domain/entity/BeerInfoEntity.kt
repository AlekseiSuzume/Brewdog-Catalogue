package com.suzume.brewdogcatalogue.domain.entity

data class BeerInfoEntity(
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
    val yeast: String,
)
