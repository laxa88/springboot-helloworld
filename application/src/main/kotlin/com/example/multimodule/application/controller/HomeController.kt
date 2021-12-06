package com.example.multimodule.application.controller

import com.example.multimodule.application.model.Student
import com.example.multimodule.application.service.StudentService
import com.example.multimodule.service.MyService
import kotlinx.coroutines.flow.toList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class HomeController(
    @Autowired val myService: MyService,
    @Autowired val studentService: StudentService
) {

    @GetMapping("/")
    fun home() = "hello world"

    // Note: MyService config message MUST be in `src/main/resources/application.yml`
    @GetMapping("/hello")
    fun message() = myService.message()

    @GetMapping("/students")
    suspend fun getStudents(): List<Student> =
        studentService.findAll().toList()

    @GetMapping("/students/{id}")
    suspend fun getStudentById(@PathVariable id: Int): Student? =
        studentService.findById(id)

    @GetMapping("/students/course/{course}")
    suspend fun getByCourseName(@PathVariable course: String): List<Student> =
        studentService.findByCourseName(course)
}
