package com.example.multimodule.application

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

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
 */
@SpringBootApplication(scanBasePackages = ["com.example.multimodule"])
@EnableR2dbcRepositories("com.example.multimodule.application.repository")
class DemoApplication

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}
