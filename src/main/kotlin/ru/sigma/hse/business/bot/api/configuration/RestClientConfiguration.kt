package ru.sigma.hse.business.bot.api.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.web.client.RestClient

@Configuration
class RestClientConfiguration {
    @Primary
    @Bean("restClient")
    fun restClient(): RestClient {
        return RestClient.create()
    }
}