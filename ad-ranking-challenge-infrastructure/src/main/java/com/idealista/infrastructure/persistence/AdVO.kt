package com.idealista.infrastructure.persistence

data class AdVO(var id: Int, var typology: String, var description: String, var pictures: List<Int>, var houseSize: Int, var gardenSize: Int?, var score: Int?, var irrelevantSince: Long?) {

}