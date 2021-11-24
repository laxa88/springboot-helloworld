package com.wyleong.myspringboot.controller

import com.wyleong.myspringboot.model.Bank
import com.wyleong.myspringboot.service.BankService
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.web.client.RestTemplate

/**
 * This way of testing mocks out the dependencies (service, restTemplate Beans),
 * and only explicitly tests the web layer. Probably not ideal because most web
 * layers just invokes the service, and the service/repository is where the
 * business logic is usually at.
 *
 * Pro: Super fast, because we don't setup the Springboot Application.
 * Con: We have to mock out all Bean dependencies.
 *
 * Notes:
 * - The @WebMvcTest filters a specific controller for this test
 * - We can use @MockBean to magically inject a mock dependencies
 */
@WebMvcTest(BankController::class)
class BankControllerTest2 {
    @MockBean
    lateinit var bankService: BankService

    @MockBean
    lateinit var restTemplate: RestTemplate

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `should get list of banks`() {
        // given
        Mockito.`when`(bankService.getBanks()).thenReturn(
            listOf(
                Bank("abc1", 1.0, 11),
                Bank("abc2", 2.0, 22),
                Bank("abc3", 3.0, 33),
            )
        )

        // when/then
        mockMvc.get("/api/banks")
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$[0].accountNumber") { value("abc1") }
            }
    }
}
