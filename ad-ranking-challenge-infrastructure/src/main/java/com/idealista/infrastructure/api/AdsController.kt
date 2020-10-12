package com.idealista.infrastructure.api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class AdsController {
    //TODO añade url del endpoint
    fun qualityListing(): ResponseEntity<List<QualityAd>> {
        //TODO rellena el cuerpo del método
        return ResponseEntity.notFound().build()
    }

    //TODO añade url del endpoint
    fun publicListing(): ResponseEntity<List<PublicAd>> {
        //TODO rellena el cuerpo del método
        return ResponseEntity.notFound().build()
    }

    //TODO añade url del endpoint
    fun calculateScore(): ResponseEntity<Void> {
        //TODO rellena el cuerpo del método
        return ResponseEntity.notFound().build()
    }
}