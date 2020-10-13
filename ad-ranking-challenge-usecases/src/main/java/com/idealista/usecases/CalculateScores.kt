package com.idealista.usecases

import com.idealista.domain.Ad
import com.idealista.domain.AdRepository
import com.idealista.domain.rules.ScoreRule
import com.idealista.usecases.score.params.CalculateScoresParams
import com.idealista.usecases.shared.Either
import com.idealista.usecases.shared.UseCase
import com.idealista.usecases.shared.Validation

class CalculateScores(private val adRepository: AdRepository,
                      private val scoreRules: List<ScoreRule>) : UseCase<CalculateScoresParams, Any> {
    override fun execute(params: CalculateScoresParams): Either<Validation, Any> {
        adRepository.saveAll(adRepository.findAll().map(this::calculateScore))
        return Either.Right(Any())
    }

    private fun calculateScore(ad: Ad): Ad {
        return ad.copy(score = scoreRules.map { it.apply(ad) }.sum())
    }
}