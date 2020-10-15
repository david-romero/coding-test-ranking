package com.idealista.domain

import com.idealista.domain.rules.ScoreRule
import java.time.Instant

interface AdIdentifier

data class Ad(val id: AdIdentifier, val typology: Typology, val description: Description, val pictures: List<Picture>, val houseSize: Int, val gardenSize: Int?, val irrelevantSince: Instant?, val score: Score = Score.empty()) {
    constructor(id: AdIdentifier, typology: Typology, description: String, pictures: List<Picture>, houseSize: Int) : this(id, typology, Description(description), pictures, houseSize, null, null)

    constructor(id: AdIdentifier, typology: Typology, description: String, pictures: List<Picture>, houseSize: Int, gardenSize: Int) : this(id, typology, Description(description), pictures, houseSize, gardenSize, null)

    fun applyScoreRules(scoreRules: List<ScoreRule>): Ad {
        val adWithNewScore = copy(score = Score(scoreRules.reduce { acc, scoreRule -> ScoreRule.SumScoreRules(acc, scoreRule) }.apply(this)))
        if (adWithNewScore.isIrrelevant()) {
            return adWithNewScore.copy(irrelevantSince = Instant.now())
        }
        return adWithNewScore
    }

    fun isRelevant() = score.hasReachedTheLimit()

    fun isIrrelevant() = !isRelevant()

    fun hasDescription() = description.isPresent()

    private fun hasPictures() = pictures.isNotEmpty()

    fun hasNoPictures() = pictures.isEmpty()

    fun isComplete() = run {
        when (typology) {
            Typology.FLAT -> {
                description.isPresent() &&
                        hasPictures() &&
                        houseSize > 0
            }
            Typology.CHALET -> {
                description.isPresent() &&
                        hasPictures() &&
                        houseSize > 0 &&
                        gardenSize ?: 0 > 0
            }
            Typology.GARAGE -> {
                hasPictures()
            }
        }
    }
}