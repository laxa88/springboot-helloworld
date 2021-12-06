package com.example.multimodule.application.service

import com.example.multimodule.application.model.Student
import com.example.multimodule.application.repository.StudentRepository
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@DataR2dbcTest
internal class StudentServiceTest {

    @Autowired
    lateinit var studentRepository: StudentRepository

    @Container
    val container = PostgreSQLContainer<Nothing>("postgres").apply {
        withDatabaseName("r2dbc:tc:postgresql://localhost:1234/school?TC_IMAGE_TAG=5.7.34")
        withExposedPorts(1234)
        withUsername("postgres")
        withPassword("password")
    }

    @Test
    fun contextLoads() {
        Assertions.assertThat(studentRepository).isNotNull
    }

    @Test
    fun `should save`() {
        // FIXME: repository fails to connect "localhost:1234"??
        runBlocking {
            studentRepository.save(Student(3, "dName", "dCourse"))
        }
    }
}
