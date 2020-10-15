package com.idealista.infrastructure.api

data class QualityAd(
        var id: Int? = null,
        var typology: String? = null,
        var description: String? = null,
        var pictureUrls: List<String>? = null,
        var houseSize: Int? = null,
        var gardenSize: Int? = null,
        var score: Int? = null,
        var irrelevantSince: String? = null)