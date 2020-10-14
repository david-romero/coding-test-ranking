package com.idealista.usecases

import com.idealista.domain.rules.Ads
import com.idealista.usecases.ad.params.ShowAdsParams
import com.idealista.usecases.shared.Either
import com.idealista.usecases.shared.UseCase
import com.idealista.usecases.shared.Validation

class ShowAds : UseCase<ShowAdsParams, Ads> {
    override fun execute(params: ShowAdsParams): Either<Validation, Ads> {
        return Either.Left(NotImplemented("Not Implemented yet"))
    }
}

inline class NotImplemented(private val error: String) : Validation {
    override fun hasErrors(): Boolean = true

    override fun getErrors(): List<String> = listOf(error)
}