package com.idealista.acceptance.config.stubs

import com.idealista.domain.*
import com.idealista.infrastructure.persistence.AdVO
import com.idealista.infrastructure.persistence.PictureVO
import java.util.*

class InMemoryPersistence : AdRepository, PictureRepository {
    val database: MutableMap<String, AdVO>
    private val pictures: MutableList<PictureVO>

    init {
        database = HashMap()
        pictures = ArrayList<PictureVO>()
        pictures.add(PictureVO(1, "http://www.idealista.com/pictures/1", "SD"))
        pictures.add(PictureVO(2, "http://www.idealista.com/pictures/2", "HD"))
        pictures.add(PictureVO(3, "http://www.idealista.com/pictures/3", "SD"))
        pictures.add(PictureVO(4, "http://www.idealista.com/pictures/4", "HD"))
        pictures.add(PictureVO(5, "http://www.idealista.com/pictures/5", "SD"))
        pictures.add(PictureVO(6, "http://www.idealista.com/pictures/6", "SD"))
        pictures.add(PictureVO(7, "http://www.idealista.com/pictures/7", "SD"))
        pictures.add(PictureVO(8, "http://www.idealista.com/pictures/8", "HD"))
    }

    override fun findAll(): List<Ad> {
        return database.values.map {
            mapToDomain(it)
        }.toList()
    }

    override fun saveAll(ads: List<Ad>): List<Ad> {
        ads.map {
            mapToVO(it)
        }.forEach {
            save(it)
        }
        return ads
    }

    override fun findByIdentifier(identifier: PictureIdentifier): Picture? {
        return pictures.find {
            it.id == identifier.toString().toInt()
        }.let {
            mapToDomain(it)
        }
    }

    private fun save(adVO: AdVO) {
        database[adVO.id.toString()] = adVO
    }

    private fun mapToDomain(it: PictureVO?) =
            Picture(IntBasedPictureIdentifier(it?.id ?: 0), it?.url ?: "", Quality.fromAcronym(it?.quality ?: ""))

    private fun mapToVO(ad: Ad) =
            AdVO(ad.id.toString().toInt(), ad.typology.name, ad.description, ad.pictures.map { it.toString().toInt() }, ad.houseSize, ad.gardenSize, ad.score.points, ad.irrelevantSince)

    private fun mapToDomain(adVO: AdVO) =
            Ad(StringBasedAdIdentifier(adVO.id.toString()), Typology.valueOf(adVO.typology), adVO.description, adVO.pictures.map { id -> IntBasedPictureIdentifier(id) }.toList(), adVO.houseSize, adVO.gardenSize, adVO.irrelevantSince, adVO.score?.let { Score(it) } ?: Score.empty())
}

inline class StringBasedAdIdentifier(private val value: String) : AdIdentifier {
    override fun toString(): String {
        return value
    }
}

inline class IntBasedPictureIdentifier(private val value: Int) : PictureIdentifier {
    override fun toString(): String {
        return "$value"
    }
}