package com.idealista.domain

import java.time.OffsetDateTime

data class IrrelevantAd(val ad: Ad, val since: OffsetDateTime) {
}