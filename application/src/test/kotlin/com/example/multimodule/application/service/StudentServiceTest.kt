package com.example.multimodule.application.service

import com.example.multimodule.application.model.Student
import com.example.multimodule.application.repository.StudentRepository
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Testcontainers

/**
 * Note: Prefer @SpringBootTest to @DataR2dbcTest, because we want to
 * also include the Flyway migrations so that the DB structure for tests
 * always matches the application.
 *
 * Note: @BeforeAll by default is static, but Kotlin doesn't allow static
 * methods. TestInstance.Lifecycle.PER_CLASS enables non-static BeforeAll
 * methods to be used.
 */
@SpringBootTest
@Testcontainers
@ActiveProfiles("test") // reads from application-{profile_name}.yml
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class StudentServiceTest {

    @Autowired
    lateinit var studentRepository: StudentRepository

    // Note: @Container will recreate a new instance between each tests... Which is slow.
    // @Container
    val container = PostgreSQLContainer<Nothing>("postgres").apply {
        //
        // withReuse(true)

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

    @BeforeAll
    fun beforeAll() {
        container.start()
    }

    @Test
    fun contextLoads() {
        Assertions.assertThat(studentRepository).isNotNull
    }

    /**
     * Don't use @DirtiesContext because it ultimately recreates a new instance
     * between tests that mutates the DB, which is basically all repository test.
     * We should instead use @AfterEach or @AfterAll to manually reset the database.
     */
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

    @Test
    fun `should save 2`() {
        runBlocking {
            // Given
            val student = Student(id = 2, name = "dName2", courseName = "dCourse2")

            // When
            val newStudent = studentRepository.save(student)

            val foo = studentRepository.findAll().toList()
            println("### $foo")

            // Then
            val updated = studentRepository.findById(newStudent.id!!)
            Assertions.assertThat(updated).isEqualTo(newStudent)
        }
    }
}
