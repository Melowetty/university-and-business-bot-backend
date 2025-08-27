package ru.sigma.hse.business.bot.api.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient

@Configuration
class YandexDiskRestConfiguration {
    @Value("\${integrations.yandex-disk.base-url}")
    private lateinit var baseUrl: String

    @Value("\${integrations.yandex-disk.token}")
    private lateinit var oauthToken: String

    @Bean("yandexDiskRestClient")
    fun restClient(): RestClient {
        return RestClient.builder()
            .baseUrl(baseUrl)
            .defaultHeader("Authorization", "OAuth $oauthToken")
            .build()
    }
}