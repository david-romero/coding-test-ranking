package com.idealista.usecases.shared

interface Validation {

    fun hasErrors(): Boolean

    fun getErrors(): List<String>

}