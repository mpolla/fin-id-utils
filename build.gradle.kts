plugins {
    kotlin("jvm") version "1.3.50"
    id("org.jetbrains.dokka") version "0.9.18"
    `maven-publish`
}

group = "com.github.mpolla"
version = "0.1"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation("junit:junit:4.12")
    testImplementation("org.apache.logging.log4j:log4j-api:2.12.1")
    testImplementation("org.apache.logging.log4j:log4j-core:2.12.1")
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/mpolla/fin-id-utils")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
            }
        }
    }
    publications {
        register("gpr", MavenPublication::class) {
            from(
                    components["java"]
            )
        }
    }
}






