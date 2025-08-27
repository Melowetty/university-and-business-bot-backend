package ru.sigma.hse.business.bot.service

import org.springframework.stereotype.Service
import ru.sigma.hse.business.bot.api.controller.model.NewCompanyRequest
import ru.sigma.hse.business.bot.domain.entity.CompanyEntity
import ru.sigma.hse.business.bot.persistence.repository.CompanyRepository
import ru.sigma.hse.business.bot.service.qr.SimpleQrCodeGenerator
import java.io.File

@Service
class CompanyService(
    private val simpleQrCodeGenerator: SimpleQrCodeGenerator,
    private val companyRepository: CompanyRepository
) {
    fun createCompany(expectedData: NewCompanyRequest): String {
        var newCompany = CompanyEntity(
            code = expectedData.code,
            name = expectedData.name,
            description = expectedData.description,
            vacanciesLink = expectedData.vacanciesLink
        )
        companyRepository.save(newCompany)

        // Генерируем QR-код
        val qrCode = simpleQrCodeGenerator.generateQrCode(expectedData.code)
        File("qr_" + expectedData.name + ".png").writeBytes(qrCode)
        return "qr for " + expectedData.name + " done.\nCode = " + expectedData.code
    }

    fun visitCompany(id: Long): String {
        // Вызываем старт ивента или компании в боте, а там бот сам будет логику делать
        System.out.println(id)
        var company = "None"
        when (id.toInt()){
            1234 -> company = "Вышка"
            4321 -> company = "Кккконтур"
            9090 -> company = "Яндекс"
            8081 -> company = "lockalhost"
            1111 -> company = "Apple"
            else -> company = "нет такой компании"
        }
        return "Вы посетили станицию '"+ company +"'. Так держать!\nВаш счёт: N посещенных точек"
    }
}