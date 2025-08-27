package ru.sigma.hse.business.bot.service

import org.springframework.stereotype.Service
import ru.sigma.hse.business.bot.api.controller.model.NewCompanyRequest
import ru.sigma.hse.business.bot.persistence.CompanyStorage
import ru.sigma.hse.business.bot.persistence.repository.CompanyRepository
import ru.sigma.hse.business.bot.service.code.CodeGenerator
import ru.sigma.hse.business.bot.service.qr.SimpleQrCodeGenerator
import java.io.File

@Service
class CompanyService(
    private val simpleQrCodeGenerator: SimpleQrCodeGenerator,
    private val codeGenerator: CodeGenerator,
    private val companyStorage: CompanyStorage
) {
    fun createCompany(expectedData: NewCompanyRequest): String {
        val code = codeGenerator.generateCompanyCode()
        companyStorage.createCompany(
            code = code,
            name = expectedData.name,
            description = expectedData.description,
            vacanciesLink = expectedData.vacanciesLink
        )

        // Генерируем QR-код
        val qrCode = simpleQrCodeGenerator.generateQrCode(code)
        File("qr_" + expectedData.name + ".png").writeBytes(qrCode)
        return "qr for " + expectedData.name + " done.\nCode = " + code
    }
}