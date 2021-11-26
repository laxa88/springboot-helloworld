package com.example.multimodule.application

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

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
class DemoApplication

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}
