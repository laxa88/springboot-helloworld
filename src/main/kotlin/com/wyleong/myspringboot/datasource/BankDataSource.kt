package com.wyleong.myspringboot.datasource

import com.wyleong.myspringboot.model.Bank

interface BankDataSource {

    fun retrieveBanks(): Collection<Bank>

    fun retrieveBank(accountNumber: String): Bank
}
