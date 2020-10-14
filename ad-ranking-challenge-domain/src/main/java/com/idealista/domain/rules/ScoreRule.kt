package com.idealista.domain.rules

import com.idealista.domain.Ad

interface ScoreRule {

    fun apply(ad: Ad): Int

    class SumScoreRules(private val leftRule: ScoreRule, private val rightRule: ScoreRule) : ScoreRule {
        override fun apply(ad: Ad): Int = leftRule.apply(ad) + rightRule.apply(ad)
    }

}