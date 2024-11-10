plugins {
    id("java")
    id("org.springframework.boot") version "3.3.3"
    id("io.spring.dependency-management") version "1.1.6"
    id("com.google.cloud.tools.jib") version "3.4.4"
}

group = "io.github.eappezo"
version = "1.0"

repositories {
    mavenCentral()
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:3.3.3")
    }
}

dependencies {
    // spring framework
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    // libraries
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    implementation("com.github.f4b6a3:ulid-creator:5.2.3")

    runtimeOnly("mysql:mysql-connector-java:8.0.33")

    implementation("io.jsonwebtoken:jjwt-api:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6")

    implementation("com.google.firebase:firebase-admin:9.1.1")

    annotationProcessor("com.querydsl:querydsl-apt:5.0.0:jakarta")
    annotationProcessor("jakarta.annotation:jakarta.annotation-api")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api")
    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    implementation("com.querydsl:querydsl-apt:5.0.0:jakarta")

    runtimeOnly("io.micrometer:micrometer-registry-prometheus")

    // testing
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    //docs
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.test {
    useJUnitPlatform()
}

jib {
    val imageTag = System.getenv("IMAGE_TAG")
    val serverPort = System.getenv("SERVER_PORT")
    val activeProfile = System.getenv("ACTIVE_PROFILE")
    val imageName = System.getenv("IMAGE_NAME")
    from {
        image = "openjdk:21-jdk"
    }
    to {
        image = "$imageName:$imageTag"
        tags = setOf("latest", imageTag)
        auth {
            username = System.getenv("DOCKER_USERNAME")
            password = System.getenv("DOCKER_PASSWORD")
        }
    }
    container {
        jvmFlags = listOf(
            "-Xms512m",
            "-Xmx512m",
            "-Dserver.port=$serverPort",
            "-Dspring.profiles.active=$activeProfile"
        )
        ports = listOf(serverPort)
    }
}