plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")

    // Magically resolves @SpringBootApplication "may not be final" error
    kotlin("plugin.spring")
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":library"))

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
