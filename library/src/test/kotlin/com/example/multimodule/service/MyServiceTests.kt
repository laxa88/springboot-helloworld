package com.example.multimodule.service

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [MyService::class])
class MyServiceTests(
    @Autowired val myService: MyService,
) {

    @Test
    fun contextLoads() {
        Assertions.assertThat(myService.message()).isEqualTo("abc123")
    }
}
