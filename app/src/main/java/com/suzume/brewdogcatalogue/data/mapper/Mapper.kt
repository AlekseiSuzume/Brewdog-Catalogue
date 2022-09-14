package com.suzume.brewdogcatalogue.data.mapper

import com.suzume.brewdogcatalogue.data.database.BeerInfoDbModel
import com.suzume.brewdogcatalogue.data.network.model.BeerInfoDto
import com.suzume.brewdogcatalogue.data.network.model.HopDto
import com.suzume.brewdogcatalogue.data.network.model.MaltDto
import com.suzume.brewdogcatalogue.domain.entity.BeerInfoEntity
import javax.inject.Inject

class Mapper @Inject constructor () {

    fun mapBeerInfoDtoToDbModel(dto: BeerInfoDto): BeerInfoDbModel {
        return BeerInfoDbModel(
            id = dto.id,
            favourite = false,
            name = dto.name ?: "",
            tagLine = dto.tagLine ?: "",
            description = dto.description ?: "",
            imageUrl = dto.imageUrl ?: "",
            abv = dto.abv ?: "n/a",
            ibu = dto.ibu ?: "n/a",
            targetOg = ogToPlato(dto.targetOg),
            malts = mapMaltDtoToList(dto.ingredientsDto?.malt),
            hops = mapHopDtoToList(dto.ingredientsDto?.hops),
            yeast = dto.ingredientsDto?.yeast ?: ""
        )
    }

    fun mapBeerInfoDbModelToEntity(dbModel: BeerInfoDbModel): BeerInfoEntity {
        return BeerInfoEntity(
            id = dbModel.id,
            favourite = dbModel.favourite,
            name = dbModel.name,
            tagLine = dbModel.tagLine,
            description = dbModel.description,
            imageUrl = dbModel.imageUrl,
            abv = dbModel.abv,
            ibu = dbModel.ibu,
            targetOg = dbModel.targetOg,
            malts = dbModel.malts,
            hops = dbModel.hops,
            yeast = dbModel.yeast
        )
    }

    private fun mapMaltDtoToList(maltList: List<MaltDto>?): String {
        return maltList?.map { it.name }?.distinct()?.joinToString(", ") ?: ""
    }

    private fun mapHopDtoToList(hopList: List<HopDto>?): String {
        return hopList?.map { it.name }?.distinct()?.joinToString(", ") ?: ""
    }

    private fun ogToPlato(og: String?): String {
        return if (og == null) {
            "n/a"
        } else {
            ((og.toDouble() % 1000) / 4)
                .toBigDecimal()
                .setScale(1, java.math.RoundingMode.DOWN).toString()
        }
    }

}