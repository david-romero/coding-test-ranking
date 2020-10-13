package com.idealista


import com.idealista.infrastructure.di.BeansInitializer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Main

fun main(args: Array<String>) {
    runApplication<Main>(*args) {
        addInitializers(
                BeansInitializer()
        )
    }
}