package com.example.multimodule.application.controller

import com.example.multimodule.service.MyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HomeController(
    @Autowired val myService: MyService
) {

    @GetMapping("/")
    fun home() = "hello world"

    // Note: MyService config message MUST be in `src/main/resources/application.yml`
    @GetMapping("/hello")
    fun message() = myService.message()
}
