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
    // Import local dependencies
    implementation(project(":library"))

    // Base project dependencies
    implementation("org.springframework.boot:spring-boot-starter-actuator:2.5.6")
    implementation("org.springframework.boot:spring-boot-starter-web:2.5.6")
    testImplementation("org.springframework.boot:spring-boot-starter-test:2.5.6")

    // R2DBC
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc:2.5.6")
    implementation("org.jetbrains.kotlin:kotlin-reflect") // helps Spring to create Beans
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions:1.1.5") // For reactive db
    runtimeOnly("io.r2dbc:r2dbc-postgresql:0.8.10.RELEASE") // Driver to interact with Postgres DB
    implementation("name.nkonev.r2dbc-migrate:r2dbc-migrate-spring-boot-starter:1.7.8") // Automates flyway migration
}
