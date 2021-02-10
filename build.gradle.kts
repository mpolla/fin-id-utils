plugins {
    kotlin("jvm") version "1.4.30"
    id("org.jetbrains.dokka") version "0.9.18"
    `java-library`
    `maven-publish`
    signing
}

group = "com.github.mpolla"
version = "0.3"
description = "Utilities for the Finnish personal identity code system"

repositories {
    mavenCentral()
    jcenter()
}

kotlin {
    explicitApi()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation("junit:junit:4.12")
    testImplementation("org.apache.logging.log4j:log4j-api:2.12.1")
    testImplementation("org.apache.logging.log4j:log4j-core:2.12.1")
}

// following https://dev.to/kengotoda/deploying-to-ossrh-with-gradle-in-2020-1lhi
java {
    withJavadocJar()
    withSourcesJar()
}

publishing {

    repositories {
        maven {
            setUrl("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = project.properties["ossrhUsername"] as String? ?: "Unknown user"
                password = project.properties["ossrhPassword"] as String? ?: "Unknown user"
            }
        }
    }

    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            pom {
                name.set(rootProject.name)
                packaging = "jar"
                description.set(project.description)
                url.set("https://github.com/mpolla/fin-id-utils")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("mpolla")
                        name.set("Matti Pöllä")
                        email.set("mpo@iki.fi")
                    }
                }
                scm {
                    connection.set("scm:git:https://github.com/mpolla/fin-id-utils.git")
                    developerConnection.set("scm:git:https://github.com/mpolla/fin-id-utils.git")
                    url.set("https://github.com/mpolla/fin-id-utils.git")
                }
            }
        }
    }
}

// gpg --list-keys --keyid-format 0xSHORT
// gradle publish -Psigning.gnupg.keyName=0xDEADBEEF
signing {
    useGpgCmd()
    sign(publishing.publications["mavenJava"])
}
