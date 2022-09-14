package com.suzume.brewdogcatalogue.data.network.model

import com.google.gson.annotations.SerializedName

data class IngredientsDto(
    @SerializedName("malt")
    val malt: List<MaltDto>?,
    @SerializedName("hops")
    val hops: List<HopDto>?,
    @SerializedName("yeast")
    val yeast: String?
)
