package com.idealista.usecases

import com.idealista.domain.*
import com.idealista.usecases.stubs.IntBasedPictureIdentifier
import java.time.Instant
import java.time.OffsetDateTime

fun ad(lambda: AdBuilder.() -> Unit): Ad = AdBuilder().apply(lambda).build()

class AdBuilder {

    private var id = ""

    private var typology = Typology.CHALET

    private var description = ""

    private var pictures = mutableListOf<Picture>()

    private var houseSize = 0

    private var gardenSize: Int? = null

    private var irrelevantSince: Instant? = null

    private var score = Int.MIN_VALUE

    fun id(lambda: () -> String) {
        this.id = lambda()
    }

    fun typology(lambda: () -> Typology) {
        this.typology = lambda()
    }

    fun description(lambda: () -> String) {
        this.description = lambda()
    }

    fun pictures(lambda: PictureListBuilder.() -> Unit) {
        pictures.addAll(PictureListBuilder().apply(lambda).build())
    }

    fun houseSize(lambda: () -> Int) {
        this.houseSize = lambda()
    }

    fun gardenSize(lambda: () -> Int) {
        this.gardenSize = lambda()
    }

    fun irrelevantSince(lambda: () -> String) {
        this.irrelevantSince = OffsetDateTime.parse(lambda()).toInstant()
    }

    fun score(lambda: () -> Int) {
        this.score = lambda()
    }

    fun build(): Ad = Ad(StringBasedAdIdentifier(id), typology, Description(description), pictures, houseSize, gardenSize, irrelevantSince, Score(score))

}

class PictureListBuilder {
    private val pictureList = mutableListOf<Picture>()

    fun picture(lambda: PictureBuilder.() -> Unit) {
        pictureList.add(PictureBuilder().apply(lambda).build())
    }

    fun build() = pictureList
}

class PictureBuilder {
    private var id: Int = 0
    private var url = ""
    private var quality = Quality.UNDEFINED

    fun id(lambda: () -> Int) {
        this.id = lambda()
    }

    fun url(lambda: () -> String) {
        this.url = lambda()
    }

    fun quality(lambda: () -> Quality) {
        this.quality = lambda()
    }

    fun build() = Picture(IntBasedPictureIdentifier(id), url, quality)
}

inline class StringBasedAdIdentifier(val value: String) : AdIdentifier