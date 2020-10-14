package com.idealista.usecases.score.params

import com.idealista.usecases.shared.ParameterValidation
import com.idealista.usecases.shared.UseCaseParams
import com.idealista.usecases.shared.Validation

class CalculateScoresParams : UseCaseParams {
    override fun validate(): Validation = ParameterValidation()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return true
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }


}