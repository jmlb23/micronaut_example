import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.21"
    id("org.jetbrains.kotlin.kapt") version "1.6.20"
}

group = "com.github.jmlb23.exampleapi.infra"

repositories {
    mavenCentral()
}

dependencies {
    api(project(":domain"))
    api(project(":infra"))
    kapt(platform("io.micronaut:micronaut-bom:3.4.3"))
    kapt("io.micronaut:micronaut-inject-java")
    implementation(platform("io.micronaut:micronaut-bom:3.4.3"))
    implementation("io.micronaut:micronaut-inject")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}