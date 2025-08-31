package ru.sigma.hse.business.bot.api.configuration

import io.mockk.mockkClass
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.annotations.servers.Server
import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@OpenAPIDefinition(
    info = Info(
        title = "HSE University&Business Conference API",
        version = "0.9-SNAPSHOT",
    ),
    servers = [
        Server(
            url = "https://ub.hse-perm-helper.ru",
            description = "Production server"
        ),
        Server(
            url = "localhost:8080/api",
            description = "Local server for development"
        ),
    ]
)
@SecurityScheme(
    name = "Auth",
    scheme = "basic",
    type = SecuritySchemeType.HTTP,
)
@Configuration
@Profile("openapi-gen")
class OpenApiConfiguration {
    @Bean
    fun mockBeanFactory(): BeanFactoryPostProcessor {
        return BeanFactoryPostProcessor { beanFactory ->
            val beanDefinitionNames = beanFactory.beanDefinitionNames

            beanDefinitionNames.forEach { beanName ->
                val beanDefinition = beanFactory.getBeanDefinition(beanName)
                val beanClassName = beanDefinition.beanClassName

                if (beanClassName?.contains("ru.sigma.hse.business.bot.persistence") == true ||
                    beanClassName?.contains("ru.sigma.hse.business.bot.service") == true
                ) {
                    val beanClass = Class.forName(beanDefinition.beanClassName)
                        ?: return@forEach

                    val mockBean = mockkClass(beanClass.kotlin)
                    beanFactory.registerSingleton("$beanName#Mock", mockBean)
                    beanFactory.registerAlias("$beanName#Mock", beanName)
                }
            }
        }
    }
}