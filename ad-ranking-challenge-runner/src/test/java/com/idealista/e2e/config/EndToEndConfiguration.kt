package com.idealista.e2e.config

import com.idealista.infrastructure.di.BeansInitializer
import org.springframework.beans.factory.NoSuchBeanDefinitionException
import org.springframework.boot.test.context.SpringBootContextLoader
import org.springframework.boot.test.web.client.LocalHostUriTemplateHandler
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.TestRestTemplate.HttpClientOption
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.boot.web.servlet.server.AbstractServletWebServerFactory
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(loader = SpringBootContextLoader::class, initializers = [BeansInitializer::class])
open class EndToEndConfiguration {

    private val defaultOptions = arrayOf<HttpClientOption>()

    private val sslOptions = arrayOf(HttpClientOption.SSL)

    @Bean
    fun testRestTemplate(applicationContext: ApplicationContext): TestRestTemplate {
        val builder = getRestTemplateBuilder(applicationContext)
        val sslEnabled = isSslEnabled(applicationContext)
        val template = TestRestTemplate(builder, null, null,
                *if (sslEnabled) sslOptions else defaultOptions)
        val handler = LocalHostUriTemplateHandler(
                applicationContext.environment, if (sslEnabled) "https" else "http")
        template.setUriTemplateHandler(handler)
        return template
    }

    private fun isSslEnabled(context: ApplicationContext): Boolean {
        return try {
            val webServerFactory = context.getBean(AbstractServletWebServerFactory::class.java)
            (webServerFactory.ssl != null && webServerFactory.ssl.isEnabled)
        } catch (ex: NoSuchBeanDefinitionException) {
            false
        }
    }

    private fun getRestTemplateBuilder(applicationContext: ApplicationContext): RestTemplateBuilder {
        return try {
            applicationContext.getBean(RestTemplateBuilder::class.java)
        } catch (ex: NoSuchBeanDefinitionException) {
            RestTemplateBuilder()
        }
    }

}