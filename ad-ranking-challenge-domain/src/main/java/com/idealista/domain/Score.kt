package com.idealista.domain

data class Score(val points: Int) {

    fun hasReachedTheLimit() = points >= SCORE_LIMIT

    companion object {
        fun of(points: Int) = Score(points)

        fun empty() = Score(Int.MIN_VALUE)

        const val SCORE_LIMIT = 40
    }

}