package ru.sigma.hse.business.bot.api.configuration

import io.mockk.mockkClass
import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

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