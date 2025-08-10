plugins {
    `java-library`

    `maven-publish`
}

group = "dev.hollowcube"
version = System.getenv("VERSION") ?: "dev"
description = "Schematic reader and writer for Minestom"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(libs.minestom)
    testImplementation(libs.minestom)

    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.platform.launcher)
    testImplementation(libs.bundles.logback)
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(21)

    withSourcesJar()
    withJavadocJar()
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    repositories {
        maven {
            url = uri("https://mvn.allycraft.de/snapshots")
            credentials {
                username = System.getenv("MAVEN_USERNAME")
                password = System.getenv("MAVEN_PASSWORD")
            }
        }
    }

    publications {
        if(project.version != "dev") {
            create<MavenPublication>("maven") {
                version = project.version.toString()

                from(components["java"])
            }
        }

        create<MavenPublication>("mavenSnapshot") {
            version = "1.0-SNAPSHOT"

            from(components["java"])
        }
    }
}