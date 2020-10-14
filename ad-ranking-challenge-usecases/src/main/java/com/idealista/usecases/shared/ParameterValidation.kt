package com.idealista.usecases.shared

class ParameterValidation : Validation {

    private val parameterErrors = listOf<String>()

    override fun hasErrors(): Boolean {
        return parameterErrors.isNotEmpty()
    }

    override fun getErrors(): List<String> {
        return parameterErrors
    }
}