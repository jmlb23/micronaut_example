import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.21"
    id("com.squareup.sqldelight") version "1.5.3"
}

group = "com.github.jmlb23.exampleapi.infra"

repositories {
    mavenCentral()
    google()
    mavenCentral()
}

dependencies {
    implementation(project(":domain"))
    implementation("com.squareup.sqldelight:jdbc-driver:1.5.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
    implementation("com.squareup.sqldelight:sqlite-driver:1.5.3")
    testImplementation(kotlin("test"))
    implementation("com.squareup.sqldelight:gradle-plugin:1.5.3")

}

sqldelight {
    database("Database") {
        packageName = "com.github.jmlb23.exampleapi.infra"
    }
}


tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}