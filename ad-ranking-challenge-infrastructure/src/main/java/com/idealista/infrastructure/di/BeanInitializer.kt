package com.idealista.infrastructure.di

import com.idealista.usecases.CalculateScores
import com.idealista.usecases.score.params.CalculateScoresParams
import com.idealista.usecases.shared.UseCase
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans


class BeansInitializer : ApplicationContextInitializer<GenericApplicationContext> {

    override fun initialize(context: GenericApplicationContext) =
            beans {
                bean<UseCase<CalculateScoresParams, Nothing>>() {
                    CalculateScores()
                }
            }.initialize(context)

}