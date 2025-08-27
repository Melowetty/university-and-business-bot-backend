package ru.sigma.hse.business.bot.api.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain


@Configuration
class SecurityConfiguration {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .authorizeHttpRequests {
                it.anyRequest().permitAll()//authenticated() //поменял
            }
            .httpBasic(Customizer.withDefaults())
            .csrf { it.disable() } // ← ОБЯЗАТЕЛЬНО ДОБАВЬТЕ ЭТУ СТРОЧКУ! //поменял
            .cors {
                it.disable()
            }
            .build()
    }
}