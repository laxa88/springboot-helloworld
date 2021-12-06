package com.example.multimodule.application.service

import com.example.multimodule.application.model.Student
import com.example.multimodule.application.repository.StudentRepository
import org.springframework.stereotype.Service

@Service
class StudentService(
    private val repository: StudentRepository
) {

    fun findAll(): List<Student> =
        repository.findAll().collectList().block() ?: emptyList()

    fun findById(id: String): Student? =
        repository.findById(id).block()

    // Demonstrate custom repository method
    fun findByCourseName(course: String): List<Student> =
        repository.findByCourse(course).collectList().block() ?: emptyList()
}