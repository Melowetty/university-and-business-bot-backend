plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.spring)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.jpa)
    alias(libs.plugins.spring.dependency.management)
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
    implementation("com.google.zxing:core:3.5.2")
    implementation("com.google.zxing:javase:3.5.2")

    implementation(libs.logback.encoder)
    implementation(libs.kotlin.logging)
    implementation(libs.spring.actuator)
    implementation(libs.spring.security)
    implementation(libs.spring.validation)
    implementation(libs.kotlin.reflect)
    implementation(libs.spring.jpa)
    implementation(libs.liquibase.core)
    implementation(libs.spring.web)

    runtimeOnly(libs.postgresql)

    testImplementation(libs.spring.test)
    testImplementation(libs.kotlin.junit)
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
