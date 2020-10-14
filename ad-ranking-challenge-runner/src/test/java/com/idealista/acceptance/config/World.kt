package com.idealista.acceptance.config

import com.idealista.domain.Ads
import com.idealista.domain.IrrelevantAds
import com.idealista.usecases.shared.Either
import com.idealista.usecases.shared.Validation

class World {
    var irrelevantAds: Either<Validation, IrrelevantAds>? = null
    var ads: Either<Validation, Ads>? = null
}