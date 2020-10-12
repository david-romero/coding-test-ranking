package com.idealista.domain

interface PictureRepository {

    fun findByIdentifier(identifier: PictureIdentifier): Picture?

}