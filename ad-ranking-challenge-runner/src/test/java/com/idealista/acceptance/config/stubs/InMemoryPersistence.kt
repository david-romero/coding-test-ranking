package com.idealista.acceptance.config.stubs

import com.idealista.domain.*
import com.idealista.infrastructure.persistence.AdVO
import com.idealista.infrastructure.persistence.PictureVO
import java.util.*

class InMemoryPersistence : AdRepository, PictureRepository {
    val database: MutableMap<String, AdVO>
    final val pictures: MutableList<PictureVO>

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
    } //TODO crea los métodos que necesites

    override fun findAll(): List<Ad> {
        return database.values.map {
            Ad(StringBasedAdIdentifier(it.id.toString()), Typology.valueOf(it.typology ?: ""), it.description
                    ?: "", it.pictures?.map { id -> IntBasedPictureIdentifier(id) }?.toList() ?: listOf(), it.houseSize
                    ?: 0, it.gardenSize, it.irrelevantSince , it.score ?: 0)
        }.toList()
    }

    override fun saveAll(ads: List<Ad>): List<Ad> {
        ads.map {
            AdVO(it.id.toString().toInt(), it.typology.name, it.description, it.pictures.map { it.toString().toInt() }, it.houseSize, it.gardenSize, it.score, it.irrelevantSince)
        }.forEach {
            database[it.id.toString()] = it
        }
        return ads
    }

    override fun findByIdentifier(identifier: PictureIdentifier): Picture? {
        return pictures.find {
            it.id == identifier.toString().toInt()
        }.let {
            Picture(IntBasedPictureIdentifier(it?.id ?: 0), it?.url ?: "", Quality.fromAcronym(it?.quality ?: ""))
        }
    }
}

inline class StringBasedAdIdentifier(private val value: String) : AdIdentifier{
    override fun toString(): String {
        return "$value"
    }
}
inline class IntBasedPictureIdentifier(private val value: Int) : PictureIdentifier {
    override fun toString(): String {
        return "$value"
    }
}