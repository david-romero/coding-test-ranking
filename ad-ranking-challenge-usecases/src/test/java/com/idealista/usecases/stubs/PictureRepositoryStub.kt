package com.idealista.usecases.stubs

import com.idealista.domain.Picture
import com.idealista.domain.PictureIdentifier
import com.idealista.domain.PictureRepository
import com.idealista.domain.Quality

class PictureRepositoryStub : PictureRepository {

    private val database: Map<Int, Picture>

    init {
        database = HashMap()
        database.put(1, Picture(IntBasedPictureIdentifier(1), "http://www.idealista.com/pictures/1", Quality.fromAcronym("SD")))
        database.put(2, Picture(IntBasedPictureIdentifier(2), "http://www.idealista.com/pictures/2", Quality.fromAcronym("HD")))
        database.put(3, Picture(IntBasedPictureIdentifier(3), "http://www.idealista.com/pictures/3", Quality.fromAcronym("SD")))
        database.put(4, Picture(IntBasedPictureIdentifier(4), "http://www.idealista.com/pictures/4", Quality.fromAcronym("HD")))
        database.put(5, Picture(IntBasedPictureIdentifier(5), "http://www.idealista.com/pictures/5", Quality.fromAcronym("SD")))
        database.put(6, Picture(IntBasedPictureIdentifier(6), "http://www.idealista.com/pictures/6", Quality.fromAcronym("SD")))
        database.put(7, Picture(IntBasedPictureIdentifier(7), "http://www.idealista.com/pictures/7", Quality.fromAcronym("SD")))
        database.put(8, Picture(IntBasedPictureIdentifier(8), "http://www.idealista.com/pictures/8", Quality.fromAcronym("HD")))
    }

    override fun findByIdentifier(identifier: PictureIdentifier): Picture? {
        return database[identifier.toString().toInt()]
    }
}

inline class IntBasedPictureIdentifier(private val value: Int) : PictureIdentifier {
    override fun toString(): String {
        return "$value"
    }
}