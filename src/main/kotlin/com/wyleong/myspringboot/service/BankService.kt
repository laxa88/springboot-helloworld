package com.wyleong.myspringboot.service

import com.wyleong.myspringboot.datasource.BankDataSource
import com.wyleong.myspringboot.model.Bank
import org.springframework.stereotype.Service

@Service
class BankService(private val dataSource: BankDataSource) {

    fun getBanks(): Collection<Bank> = dataSource.retrieveBanks()
}