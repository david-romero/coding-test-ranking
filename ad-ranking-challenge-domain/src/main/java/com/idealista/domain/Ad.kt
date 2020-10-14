package com.idealista.domain

import com.idealista.domain.rules.ScoreRule
import java.util.*

interface AdIdentifier

data class Ad(val id: AdIdentifier, val typology: Typology, val description: String, val pictures: List<PictureIdentifier>, val houseSize: Int, val gardenSize: Int?, val irrelevantSince: Date?, val score: Score = Score.empty()) {
    constructor(id: AdIdentifier, typology: Typology, description: String, pictures: List<PictureIdentifier>, houseSize: Int) : this(id, typology, description, pictures, houseSize, null, null)

    constructor(id: AdIdentifier, typology: Typology, description: String, pictures: List<PictureIdentifier>, houseSize: Int, gardenSize: Int) : this(id, typology, description, pictures, houseSize, gardenSize, null)

    fun applyRules(scoreRules: List<ScoreRule>): Ad {
        val newScore = scoreRules.reduce { acc, scoreRule -> ScoreRule.SumScoreRules(acc, scoreRule) }.apply(this)
        return copy(score = Score(newScore))
    }

    fun isRelevant() = score.hasReachedTheLimit()
}