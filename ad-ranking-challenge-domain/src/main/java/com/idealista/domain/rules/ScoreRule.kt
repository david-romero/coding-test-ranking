package com.idealista.domain.rules

import com.idealista.domain.Ad

interface ScoreRule {

    fun apply(ad: Ad): Int

}