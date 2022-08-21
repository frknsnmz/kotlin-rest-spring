package com.guide.rest.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.guide.rest.model.controller.TransactionModel
import com.guide.rest.model.entity.TransactionEntity
import com.guide.rest.repository.TransferRepository
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import java.util.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest
class PaymentsControllerTest(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    private lateinit var paymentsRepository: TransferRepository

    @Test
    fun `should submit transaction with success`() {
        val transfer = TransactionModel(
            value = 1.50,
            description = "store 1",
            targetAccount = "NL76ABNA2376059879"
        )

        every() { paymentsRepository.save(any()) } returns mockk()

        mockMvc.perform(
            MockMvcRequestBuilders.post("/transfer/new")
            .content(ObjectMapper().writeValueAsString(transfer))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `should get transaction with success`() {
        val mockTransaction = mockk<TransactionEntity>().apply {
            every { value } returns 1.50
            every { description } returns "store 1"
            every { accountIdentifier } returns "NL47INGB8845464385"
            every { date } returns Date()
            every { id } returns UUID.randomUUID()
        }

        every { paymentsRepository.findAll() } returns listOf(mockTransaction)

        mockMvc.perform(MockMvcRequestBuilders.get("/transfer/all").accept(MediaType.APPLICATION_JSON))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.[0].description").value("store 1"))
    }

    @Test
    fun `should return a bad request error if the request there's no body`() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/transfer/new")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().is4xxClientError)
    }
}