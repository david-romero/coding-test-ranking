package com.idealista.usecases

import com.idealista.domain.Ad
import com.idealista.domain.AdRepository
import com.idealista.domain.PictureRepository
import com.idealista.domain.Quality
import com.idealista.usecases.score.params.CalculateScoresParams
import com.idealista.usecases.shared.Either
import com.idealista.usecases.shared.UseCase
import com.idealista.usecases.shared.Validation

class CalculateScores(private val adRepository: AdRepository, private val pictureRepository: PictureRepository) : UseCase<CalculateScoresParams, Any> {
    override fun execute(params: CalculateScoresParams): Either<Validation, Any> {
        adRepository.saveAll(adRepository.findAll().map(this::calculateScore))
        return Either.Right(Any())
    }

    private fun calculateScore(it: Ad): Ad {
        return if (it.pictures.isEmpty()) {
            it.copy(score = -10)
        } else {
            val newScore = it.pictures.map {
                if (pictureRepository.findByIdentifier(it)?.quality == Quality.HIGH_DEFINITION) {
                    20
                } else {
                    10
                }
            }.sum()
            it.copy(score = newScore)
        }
    }
}