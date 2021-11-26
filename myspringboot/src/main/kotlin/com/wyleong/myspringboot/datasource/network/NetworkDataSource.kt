package com.wyleong.myspringboot.datasource.network

import com.wyleong.myspringboot.datasource.BankDataSource
import com.wyleong.myspringboot.datasource.network.dto.PlaceholderData
import com.wyleong.myspringboot.model.Bank
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate

@Repository("network")
class NetworkDataSource(
    @Autowired private val restTemplate: RestTemplate
) : BankDataSource {

    override fun retrieveBanks(): Collection<Bank> {
        val response =
            restTemplate.getForEntity(
                "https://jsonplaceholder.typicode.com/todos",
                Array<PlaceholderData>::class.java
            )

        response.body?.forEach {
            println("### $it")
        }

        return listOf(
            Bank("test", 1.0, 1)
        )
    }

    override fun retrieveBank(accountNumber: String): Bank {
        TODO("Not yet implemented")
    }

    override fun createBank(bank: Bank): Bank {
        TODO("Not yet implemented")
    }

    override fun updateBank(bank: Bank): Bank {
        TODO("Not yet implemented")
    }

    override fun deleteBank(accountNumber: String) {
        TODO("Not yet implemented")
    }
}
