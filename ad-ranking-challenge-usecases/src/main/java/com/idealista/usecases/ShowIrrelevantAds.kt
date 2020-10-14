package com.idealista.usecases

import com.idealista.domain.IrrelevantAds
import com.idealista.usecases.ad.params.ShowIrrelevantAdsParams
import com.idealista.usecases.shared.Either
import com.idealista.usecases.shared.UseCase
import com.idealista.usecases.shared.Validation

class ShowIrrelevantAds : UseCase<ShowIrrelevantAdsParams, IrrelevantAds> {
    override fun execute(params: ShowIrrelevantAdsParams): Either<Validation, IrrelevantAds> {
        return Either.Left(NotImplemented("Not Implemented yet"))
    }
}

inline class NotImplemented(private val error: String) : Validation {
    override fun hasErrors(): Boolean = true

    override fun getErrors(): List<String> = listOf(error)
}