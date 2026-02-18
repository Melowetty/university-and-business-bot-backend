package ru.sigma.hse.business.bot.service.qr

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Primary
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@Primary
@Component
class RemoteQrCodeGenerator(
    private val restClient: RestClient
) : QrCodeGenerator {
    @Value("\${integrations.qr.base-url:http://localhost:8090}")
    private lateinit var qrBaseUrl: String

    override fun generateQrCode(data: String, size: Int): ByteArray {
        val request = mapOf(
            "data" to data,
            "size" to size
        )

        val bytes = restClient.post()
            .uri("${qrBaseUrl.trimEnd('/')}/generate")
            .contentType(MediaType.APPLICATION_JSON)
            .body(request)
            .retrieve()
            .body(ByteArray::class.java)

        if (bytes == null || bytes.isEmpty()) {
            throw RuntimeException("QR service returned empty response")
        }

        logger.debug { "QR generated via external service: ${bytes.size} bytes" }
        return bytes
    }

    companion object {
        private val logger = KotlinLogging.logger { }
    }
}
