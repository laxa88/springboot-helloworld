package com.example.multimodule.application.service

import com.example.multimodule.application.model.Student
import com.example.multimodule.application.repository.StudentRepository
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest

@DataR2dbcTest
internal class StudentServiceTest {

    @Autowired
    lateinit var studentRepository: StudentRepository

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
