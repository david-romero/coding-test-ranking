package com.idealista.usecases.score.params

import com.idealista.usecases.shared.UseCaseParams
import com.idealista.usecases.shared.Validation

class CalculateScoresParams : UseCaseParams {
    override fun validate(): Validation {
        TODO("Not yet implemented")
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return true
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }


}