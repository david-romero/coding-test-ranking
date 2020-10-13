package com.idealista.domain

import java.util.*

interface AdIdentifier

data class Ad(val id: AdIdentifier, val typology: Typology, val description: String, val pictures: List<PictureIdentifier>, val houseSize: Int, val gardenSize: Int?, val irrelevantSince: Date?, val score: Int = 0) {

    constructor(id: AdIdentifier, typology: Typology, description: String, pictures: List<PictureIdentifier>, houseSize: Int) : this(id, typology, description, pictures, houseSize, null, null)
    constructor(id: AdIdentifier, typology: Typology, description: String, pictures: List<PictureIdentifier>, houseSize: Int, gardenSize: Int) : this(id, typology, description, pictures, houseSize, gardenSize, null)

}