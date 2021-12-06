package com.example.multimodule.application.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("student")
data class Student(

    // `@Id` enables repository.findById() to work magically
    @Id var id: Int? = null,

    val name: String,
    val course: String,
)
