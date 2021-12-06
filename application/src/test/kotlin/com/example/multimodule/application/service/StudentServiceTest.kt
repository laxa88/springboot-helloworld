package com.example.multimodule.application.service

import com.example.multimodule.application.model.Student
import com.example.multimodule.application.repository.StudentRepository
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@DataR2dbcTest
@ActiveProfiles("test") // reads from application-{profile_name}.yml
internal class StudentServiceTest {

    @Autowired
    lateinit var studentRepository: StudentRepository

    @Container
    val container = PostgreSQLContainer<Nothing>("postgres").apply {
        // TC_IMAGE_TAG is required but value can be empty.
        // The value is the docker image version, e.g. "bullseye" (from "postgres:bullseye")
        withDatabaseName("r2dbc:tc:postgresql://localhost:1111/school?TC_IMAGE_TAG=")

        withExposedPorts(1111)
        withUsername("postgres")
        withPassword("password")
    }

    @Test
    fun contextLoads() {
        Assertions.assertThat(studentRepository).isNotNull
    }

    @Test
    fun `should save`() {
        runBlocking {
            studentRepository.save(Student(3, "dName", "dCourse"))
        }
    }
}
