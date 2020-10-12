package com.idealista.domain

import java.util.*

interface AdIdentifier

data class Ad(val id: AdIdentifier, val typology: Typology, val description: String, val pictures: List<PictureIdentifier>, val houseSize: Int, val gardenSize: Int?, val irrelevantSince: Date?, val score: Int? = 0)