val cucumberVersion = "5.6.0"

val logback_version: String by project
val ktor_version: String by project
val kotlin_version: String by project

plugins {
    application
    kotlin("jvm") version "1.3.70"
}

group = "no.nav"
version = "0.0.1-SNAPSHOT"

application {
    mainClassName = "no.nav.medlemskap.RunCucumberKt"
}

repositories {
    mavenLocal()
    jcenter()
    maven { url = uri("https://kotlin.bintray.com/ktor") }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("io.cucumber:cucumber-junit:${cucumberVersion}")
    implementation("io.cucumber:cucumber-java8:${cucumberVersion}")
}

kotlin.sourceSets["main"].kotlin.srcDirs("src/kotlin")
sourceSets["main"].resources.srcDirs("src/resources")