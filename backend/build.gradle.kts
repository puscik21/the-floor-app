plugins {
    java
    id("org.springframework.boot") version "4.0.2"
    id("io.spring.dependency-management") version "1.1.7"
    id("pmd")
    id("checkstyle")
    id("com.github.node-gradle.node") version "7.0.2"
}

group = "com.thefloorapp"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    compileOnly("org.projectlombok:lombok:1.18.42")
    annotationProcessor("org.projectlombok:lombok:1.18.42")

    runtimeOnly("com.h2database:h2")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
    testImplementation("org.mockito:mockito-core")
    testImplementation("org.assertj:assertj-core:3.27.7")
    testImplementation("com.fasterxml.jackson.core:jackson-databind")
}

tasks.test {
    useJUnitPlatform()
}

// Disable the plain JAR - we only need the executable fat JAR
tasks.named<Jar>("jar") {
    enabled = false
}

pmd {
    isConsoleOutput = true
    toolVersion = "7.21.0"
    ruleSets = listOf()
    ruleSetFiles = files("config/pmd/ruleset.xml")
}

checkstyle {
    toolVersion = "10.13.0"
    configFile = file("config/checkstyle/checkstyle.xml")
}

val frontendDir = file("$projectDir/../frontend")

node {
    download.set(true)
    version.set("24.11.1")
    nodeProjectDir.set(file("$projectDir/../frontend"))
}

// Task to copy built frontend to Spring Boot static resources
val copyFrontendToStatic = tasks.register<Copy>("copyFrontendToStatic") {
    group = "frontend"
    description = "Copies built frontend files to Spring Boot static resources"
    dependsOn("npm_run_build") // From 'com.github.node-gradle.node' plugin

    from("$frontendDir/dist")
    into("$projectDir/src/main/resources/static")
}

tasks.named<ProcessResources>("processResources") {
    dependsOn(copyFrontendToStatic)
}

// Task to build a production-ready JAR with frontend included
val buildWithFrontend = tasks.register("buildWithFrontend") {
    group = "build"
    description = "Builds the complete application with frontend included"
    dependsOn(copyFrontendToStatic, tasks.named("build"))

    tasks.named("build").get().mustRunAfter(copyFrontendToStatic)
}

// Clean frontend files when running clean
tasks.named("clean") {
    doLast {
        delete("$frontendDir/dist")
        delete("$projectDir/src/main/resources/static")
    }
}
