package ru.sigma.hse.business.bot.api.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.sigma.hse.business.bot.service.ConferenceEndingService

@RestController
@RequestMapping("/conference")
class ConferenceController(
    private val conferenceEndingService: ConferenceEndingService,
) {
    @PostMapping("/complete")
    fun completeConference() {
        conferenceEndingService.endConference()
    }
}