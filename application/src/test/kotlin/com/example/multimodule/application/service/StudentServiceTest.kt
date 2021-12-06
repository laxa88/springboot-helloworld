package com.example.multimodule.application.service

import com.example.multimodule.application.model.Student
import com.example.multimodule.application.repository.StudentRepository
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

/**
 * Note: Prefer @SpringBootTest to @DataR2dbcTest, because we want to
 * also include the Flyway migrations so that the DB structure for tests
 * always matches the application.
 */
@SpringBootTest
@Testcontainers
@ActiveProfiles("test") // reads from application-{profile_name}.yml
internal class StudentServiceTest {

    @Autowired
    lateinit var studentRepository: StudentRepository

    // Note: @Container will recreate a new instance between each tests... Which is slow.
    @Container
    val container = PostgreSQLContainer<Nothing>("postgres").apply {
        /**
         * These are not necessary; Testcontainers will automagically connect via `application-test.yml`.
         * Below config can be used for testing against actual DB, but prefer Testcontainers
         * instead, because Testcontainers create temporary DB inside Docker for tests.
         */
        // TC_IMAGE_TAG is required; e.g. "bullseye" (from "postgres:bullseye")
        // withDatabaseName("r2dbc:tc:postgresql://localhost:1111/school?TC_IMAGE_TAG=bullseye")
        // withExposedPorts(1111)
        // withUsername("postgres")
        // withPassword("password")
    }

    @Test
    fun contextLoads() {
        Assertions.assertThat(studentRepository).isNotNull
    }

    @Test
    fun `should save`() {
        runBlocking {
            // Given
            val newStudent = Student(id = 3, name = "dName", courseName = "dCourse")

            // When
            studentRepository.save(newStudent)

            // Then
            val updated = studentRepository.findById(newStudent.id!!)
            Assertions.assertThat(updated).isEqualTo(newStudent)
        }
    }
}
