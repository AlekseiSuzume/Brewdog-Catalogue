package com.suzume.brewdogcatalogue.data.network.model

import com.google.gson.annotations.SerializedName

data class BeerInfoDto(

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String?,

    @SerializedName("tagline")
    val tagLine: String?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("image_url")
    val imageUrl: String?,

    @SerializedName("abv")
    val abv: String?,

    @SerializedName("ibu")
    val ibu: String?,

    @SerializedName("target_og")
    val targetOg: String?,

    @SerializedName("ingredients")
    val ingredientsDto: IngredientsDto?
)
