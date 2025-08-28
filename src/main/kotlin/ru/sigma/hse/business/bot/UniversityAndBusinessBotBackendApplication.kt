package ru.sigma.hse.business.bot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableAsync
class UniversityAndBusinessBotBackendApplication

fun main(args: Array<String>) {
    runApplication<UniversityAndBusinessBotBackendApplication>(*args)
}
