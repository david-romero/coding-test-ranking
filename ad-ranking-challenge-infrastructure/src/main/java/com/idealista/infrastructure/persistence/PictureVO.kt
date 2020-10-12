package com.idealista.infrastructure.persistence

class PictureVO {
    var id: Int? = null
    var url: String? = null
    var quality: String? = null

    constructor() {}
    constructor(id: Int?, url: String?, quality: String?) {
        this.id = id
        this.url = url
        this.quality = quality
    }
}