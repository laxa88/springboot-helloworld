package com.example.multimodule.application.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("student")
data class Student(

    // `@Id` enables repository.findById() to work magically
    @Id
    @Column("id")
    var id: Int? = null,

    @Column("name")
    val name: String,

    @Column("course_name")
    val courseName: String,
)
