package com.example.multimodule.application

import com.example.multimodule.service.MyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Reference: https://spring.io/guides/gs/multi-module/
 *
 * Note: @SpringBootApplication is a convenience annotation that adds:
 * - @Configuration
 * - @EnableAutoConfiguration
 * - @ComponentScan
 *
 * `scanBasePackages` specifies the parent package, so the requisite MyService
 * can be detected.
 *
 * TODO: Why do need to use `open` here, but not in MySpringBootApplication example?
 */
@SpringBootApplication(scanBasePackages = ["com.example.multimodule"])
@RestController
open class DemoApplication(
    @Autowired val myService: MyService
) {

    @GetMapping("/")
    fun home() = "hello world"

    // Note: MyService config message MUST be in `src/main/resources/application.yml`
    @GetMapping("/hello")
    fun message() = myService.message()
}

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}
