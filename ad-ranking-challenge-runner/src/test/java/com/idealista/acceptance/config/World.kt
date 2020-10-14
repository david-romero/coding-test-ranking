package com.idealista.acceptance.config

import com.idealista.domain.rules.Ads
import com.idealista.usecases.shared.Either
import com.idealista.usecases.shared.Validation

class World {
    var ads: Either<Validation, Ads>? = null
}