import org.apache.tools.ant.taskdefs.condition.Os

plugins {
    java
    id("org.springframework.boot") version "4.0.2"
    id("io.spring.dependency-management") version "1.1.7"
    id("pmd")
    id("checkstyle")
}

group = "com.thefloorapp"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
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
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    compileOnly("org.projectlombok:lombok:1.18.42")
    runtimeOnly("com.h2database:h2")
    annotationProcessor("org.projectlombok:lombok:1.18.42")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.mockito:mockito-core")
    testImplementation("org.assertj:assertj-core:3.27.7")
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

// Task to install npm dependencies
val npmInstall = tasks.register<Exec>("npmInstall") {
    group = "frontend"
    description = "Installs npm dependencies for the frontend"
    workingDir = frontendDir

    val isWindows = Os.isFamily(Os.FAMILY_WINDOWS)
    executable = if (isWindows) "npm.cmd" else "npm"
    args("install")

    inputs.file("$frontendDir/package.json")
    inputs.file("$frontendDir/package-lock.json")
    outputs.dir("$frontendDir/node_modules")
}

// Task to build the frontend
val buildFrontend = tasks.register<Exec>("buildFrontend") {
    group = "frontend"
    description = "Builds the React frontend for production"
    workingDir = frontendDir
    dependsOn(npmInstall)

    val isWindows = Os.isFamily(Os.FAMILY_WINDOWS)
    executable = if (isWindows) "npm.cmd" else "npm"
    args("run", "build")

    inputs.dir("$frontendDir/src")
    inputs.file("$frontendDir/package.json")
    inputs.file("$frontendDir/index.html")
    inputs.file("$frontendDir/vite.config.ts")
    outputs.dir("$frontendDir/dist")

    doFirst {
        logger.lifecycle("Building frontend from: ${frontendDir}")
    }
    doLast {
        val distDir = file("$frontendDir/dist")
        if (!distDir.exists() || !distDir.isDirectory) {
            throw GradleException("Frontend build failed: dist directory was not created at ${distDir.absolutePath}")
        }
        logger.lifecycle("Frontend build completed successfully. Output: ${distDir.absolutePath}")
    }
}

// Task to copy built frontend to Spring Boot static resources
val copyFrontendToStatic = tasks.register<Copy>("copyFrontendToStatic") {
    group = "frontend"
    description = "Copies built frontend files to Spring Boot static resources"
    dependsOn(buildFrontend)

    from("$frontendDir/dist")
    into("$projectDir/src/main/resources/static")
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

