package com.idealista.domain

interface PictureIdentifier

data class Picture(val identifier: PictureIdentifier, val url: String, val quality: Quality)

enum class Quality(val acronym: String) {
    HIGH_DEFINITION("HD"),
    STANDARD_DEFINITION("SD"),
    UNDEFINED("");

    companion object {
        fun fromAcronym(acronym: String): Quality {
            return values().find {
                it.acronym == acronym
            } ?: UNDEFINED
        }
    }
}