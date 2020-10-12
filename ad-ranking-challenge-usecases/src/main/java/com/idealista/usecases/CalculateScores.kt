package com.idealista.usecases

import com.idealista.usecases.score.params.CalculateScoresParams
import com.idealista.usecases.shared.Either
import com.idealista.usecases.shared.UseCase
import com.idealista.usecases.shared.Validation

class CalculateScores : UseCase<CalculateScoresParams, Nothing> {
    override fun execute(params: CalculateScoresParams): Either<Validation, Nothing> {
        TODO("Not yet implemented")
    }
}