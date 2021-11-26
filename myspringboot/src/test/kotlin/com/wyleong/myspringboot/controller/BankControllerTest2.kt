package com.wyleong.myspringboot.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.wyleong.myspringboot.model.Bank
import com.wyleong.myspringboot.service.BankService
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.patch
import org.springframework.test.web.servlet.post
import org.springframework.web.client.RestTemplate
import javax.validation.Validation

/**
 * This way of testing mocks out the dependencies (service, restTemplate Beans),
 * and only explicitly tests the web layer. Probably not ideal because most web
 * layers just invokes the service, and the service/repository is where the
 * business logic is usually at.
 *
 * Pro:
 * - Super fast, due to test slicing (skips Springboot Application setup).
 *
 * Con:
 * - We have to mock out all Bean dependencies.
 * - Does not assert data layer state changes.
 *
 * Notes:
 * - The @WebMvcTest filters a specific controller for this test
 * - We can use @MockBean to magically inject a mock dependencies
 */
@WebMvcTest(BankController::class)
class BankControllerTest2(
    // This is just an alternate way to write commented code below
    @MockBean @Autowired val bankService: BankService,
    @MockBean @Autowired val restTemplate: RestTemplate,
    @Autowired val mockMvc: MockMvc,
    @Autowired val objectMapper: ObjectMapper
) {

    // @MockBean
    // lateinit var bankService: BankService
    //
    // @MockBean
    // lateinit var restTemplate: RestTemplate
    //
    // @Autowired
    // lateinit var mockMvc: MockMvc

    val baseUrl = "/api/banks"

    @Nested
    @DisplayName("GET /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBanks {

        @Test
        fun `should return list of banks`() {
            // given
            Mockito.`when`(bankService.getBanks()).thenReturn(
                listOf(
                    Bank("abc1", 1.0, 11),
                    Bank("abc2", 2.0, 22),
                    Bank("abc3", 3.0, 33),
                )
            )

            // when/then
            mockMvc.get(baseUrl)
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$[0].accountNumber") { value("abc1") }
                }
        }
    }

    @Nested
    @DisplayName("GET /api/banks/{accountNumber")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBank {
        @Test
        fun `should return the bank with given account number`() {
            // given
            val accountNumber = 1234

            Mockito.`when`(bankService.getBank("1234")).thenReturn(
                Bank("abcd", 3.0, 33)
            )

            // when/then
            mockMvc.get("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.accountNumber") { value("abcd") }
                    jsonPath("$.trust") { value("3.0") }
                    jsonPath("$.transactionFee") { value("33") }
                }
        }

        @Test
        fun `should return NOT FOUND if the account number does not exist`() {
            // given
            val accountNumber = 1234

            Mockito.`when`(bankService.getBank("1234")).thenThrow(
                NoSuchElementException("dummy error message")
            )

            // when/then
            mockMvc.get("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                }
        }
    }

    @Nested
    @DisplayName("POST /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PostNewBank {

        @Test
        fun `should add the new bank`() {
            // given
            val newBank = Bank("acc123", 31.415, 2)

            Mockito.`when`(bankService.addBank(newBank)).thenReturn(newBank)

            // when
            val performPost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newBank)
            }

            // then
            performPost
                .andDo { print() }
                .andExpect {
                    status { isCreated() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(newBank))
                    }
                }
        }

        @Test
        fun `should return BAD REQUEST if bank with given account number already exists`() {
            // given
            val invalidBank = Bank("1234", 1.0, 1)

            Mockito.`when`(bankService.addBank(invalidBank)).thenThrow(
                IllegalArgumentException("Dummy exception message")
            )

            // when
            val performPost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBank)
            }

            // then
            performPost
                .andDo { print() }
                .andExpect {
                    status { isBadRequest() }
                }
        }

        @Test
        fun `should validate fields via endpoint`() {
            // given
            val invalidBank = Bank("1234", 1.0, 1, codeName = "ab")

            // when
            val performPost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBank)
            }

            // then
            performPost
                .andDo { print() }
                .andExpect {
                    status { isBadRequest() }
                }
        }

        @Test
        fun `should validate fields via validator`() {
            // given
            val invalidBank = Bank("1234", 1.0, 0, codeName = "ab")
            val validator = Validation.buildDefaultValidatorFactory().validator

            // when
            val violations = validator.validate(invalidBank)

            // then
            assertThat(violations.count()).isEqualTo(2)
            assertThat(violations).anyMatch { it.message == "FEE_MUST_BE_POSITIVE" }
            assertThat(violations).anyMatch { it.message == "INVALID_LENGTH" }
        }
    }

    @Nested
    @DisplayName("PATCH /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PatchExistingBank {

        @Test
        fun `should update an existing bank`() {
            // given
            val updatedBank = Bank("1234", 1.0, 2)

            Mockito.`when`(bankService.updateBank(updatedBank)).thenReturn(updatedBank)

            // when
            val performPatch = mockMvc.patch(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(updatedBank)
            }

            // then
            performPatch
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(updatedBank))
                    }
                }
        }

        @Test
        fun `should return NOT FOUND if no bank with given account number exists`() {
            // given
            val invalidBank = Bank("does_not_exist", 1.0, 1)

            Mockito.`when`(bankService.updateBank(invalidBank)).thenThrow(
                NoSuchElementException("Dummy exception message")
            )

            // when
            val performPatch = mockMvc.patch(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBank)
            }

            // then
            performPatch
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }
    }

    @Nested
    @DisplayName("DELETE /api/banks/{accountNumber}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class DeleteExistingBank {

        @Test
        fun `should delete the bank with the given account number`() {
            // given
            val accountNumber = "1234"

            Mockito.doNothing().`when`(bankService).deleteBank(accountNumber)

            // when/then
            mockMvc.delete("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status { isNoContent() }
                }
        }

        @Test
        fun `should return NOT FOUND if no bank with given account number exists`() {
            // given
            val invalidAccountNumber = "does_not_exist"

            Mockito.`when`(bankService.deleteBank(invalidAccountNumber)).thenThrow(
                NoSuchElementException("Dummy exception message")
            )

            // when
            val deleteRequest = mockMvc.delete("$baseUrl/$invalidAccountNumber")

            // then
            deleteRequest
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }
    }
}
