package com.example.multimodule.application.service

import com.example.multimodule.application.model.Student
import com.example.multimodule.application.repository.StudentRepository
import org.springframework.stereotype.Service

@Service
class StudentService(
    private val repository: StudentRepository
) {

    suspend fun findAll() =
        repository.findAll()

    suspend fun findById(id: Int) =
        repository.findById(id)

    // Demonstrate custom repository method
    suspend fun findByCourseName(course: String): List<Student> =
        repository.findByCourse(course).collectList().block() ?: emptyList()
}
