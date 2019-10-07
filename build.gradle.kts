plugins {
    id("org.jetbrains.kotlin.jvm") version "1.3.50"
    id("org.jetbrains.dokka") version "0.9.18"
}

group = "com.gitlab.mpolla"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
     jcenter()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testCompile("junit:junit:4.12")
    testCompile("org.apache.logging.log4j:log4j-api:2.12.1")
    testCompile("org.apache.logging.log4j:log4j-core:2.12.1")
}
