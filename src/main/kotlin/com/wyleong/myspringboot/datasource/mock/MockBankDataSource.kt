package com.wyleong.myspringboot.datasource.mock

import com.wyleong.myspringboot.datasource.BankDataSource
import com.wyleong.myspringboot.model.Bank
import org.springframework.stereotype.Repository

@Repository
class MockBankDataSource : BankDataSource {

    private val banks = listOf(
        Bank("1234", 3.14, 17),
        Bank("1010", 17.0, 0),
        Bank("5678", 0.0, 100),
    )

    override fun retrieveBanks(): Collection<Bank> = banks
}
