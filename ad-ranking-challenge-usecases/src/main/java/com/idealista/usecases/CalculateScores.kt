package com.idealista.usecases

import com.idealista.domain.AdRepository
import com.idealista.domain.rules.ScoreRule
import com.idealista.usecases.score.params.CalculateScoresParams
import com.idealista.usecases.shared.Either
import com.idealista.usecases.shared.UseCase
import com.idealista.usecases.shared.Validation

class CalculateScores(private val adRepository: AdRepository,
                      private val scoreRules: List<ScoreRule>) : UseCase<CalculateScoresParams, Any> {
    override fun execute(params: CalculateScoresParams): Either<Validation, Any> {
        adRepository.findAll()
                .map { ad ->
                    ad.applyRules(scoreRules)
                }
                .run {
                    adRepository.saveAll(this)
                }
        return Either.Right(Any())
    }
}