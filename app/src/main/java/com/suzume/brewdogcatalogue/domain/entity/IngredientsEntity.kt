package com.suzume.brewdogcatalogue.domain.entity

data class IngredientsEntity(
    val maltEntity: List<MaltEntity>?,
    val hopEntities: List<HopEntity>?,
    val yeast: String?
)
