plugins {
    // java
    application
    kotlin("jvm") version "1.5.10"
}

group = "net.xrrocha"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}
application {
    mainClass.set("net.xrrocha.minerva.poc.MainKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("de.neuland-bfi:pug4j:2.0.0-beta-3")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
