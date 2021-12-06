package com.example.multimodule.application.repository

import com.example.multimodule.application.model.Student
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface StudentRepository : CoroutineCrudRepository<Student, Int> {

    // language=SQL
    @Query("SELECT * FROM student e WHERE e.course = :course")
    fun findByCourse(course: String): Flux<Student>
}
