import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.spring)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.jpa)
    alias(libs.plugins.spring.dependency.management)
    alias(libs.plugins.springdoc.openapi)
}

group = "ru.sigma.hse.business"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.springdoc.openapi)

    implementation(libs.jackson.kotlin)

    implementation(libs.zxing.core)
    implementation(libs.zxing.javase)

    implementation(libs.logback.encoder)
    implementation(libs.kotlin.logging)

    implementation(libs.spring.actuator)
    implementation(libs.spring.security)
    implementation(libs.spring.validation)
    implementation(libs.spring.web)

    implementation(libs.spring.jpa)
    implementation(libs.kotlin.reflect)
    implementation(libs.liquibase.core)

    implementation(libs.mockK)

    implementation(libs.apache.poi)
    implementation(libs.apache.poi.ooxml)

    runtimeOnly(libs.postgresql)

    testImplementation(libs.kotlin.junit)
    testImplementation(libs.spring.test)
    testImplementation(libs.spring.security.test)

    testRuntimeOnly(libs.junit.platform.launcher)
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.Embeddable")
    annotation("javax.persistence.MappedSuperclass")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

openApi {
    apiDocsUrl = "http://localhost:8082/v3/api-docs"
    outputFileName = "openapi.json"
    outputDir.set(layout.buildDirectory.dir("api-docs").get().asFile)
    waitTimeInSeconds = 30
}

tasks.named("bootBuildImage") {
    this as BootBuildImage
    val env = mapOf("BP_HEALTH_CHECKER_ENABLED" to "true")
    environment.set(env)
    buildpacks.addAll("urn:cnb:builder:paketo-buildpacks/java", "docker.io/paketobuildpacks/health-checker:2.10.2")
}