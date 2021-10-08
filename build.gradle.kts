plugins {
    val kotlinVersion = "1.5.31"
    id("org.springframework.boot") version "2.5.5"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.asciidoctor.convert") version "1.5.12"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    kotlin("kapt") version kotlinVersion
}

group = "com.mashup"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")

    runtimeOnly("mysql:mysql-connector-java:8.0.15")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    implementation("com.querydsl:querydsl-jpa:4.4.0")
    kapt("com.querydsl:querydsl-apt:4.4.0:jpa")

    runtimeOnly("com.h2database:h2")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.1.0")
    testImplementation("org.mockito:mockito-inline:2.21.0")

    asciidoctor("org.springframework.restdocs:spring-restdocs-asciidoctor")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
}

tasks {
    test {
        useJUnitPlatform()
    }

    compileKotlin {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }

    val snippetsDir = file("build/generated-snippets")
    asciidoctor {
        inputs.dir(snippetsDir)
        dependsOn("restDocsTest")
    }

    register("restDocs") {
        dependsOn(asciidoctor)
        doLast {
            copy {
                from(file("$buildDir/asciidoc/html5"))
                into(file("docs"))
            }
        }
    }

    register<Test>("restDocsTest") {
        useJUnitPlatform()
        filter {
            includeTestsMatching("*ControllerTest")
        }
    }
}

tasks.getByName<Jar>("jar") {
    enabled = false
}
