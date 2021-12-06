package com.example.multimodule.application.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("student")
data class Student(

    // `@Id` enables repository.findById() to work magically
    @Id val id: Int,

    val name: String,
    val course: String,
    val emailId: String,
)
