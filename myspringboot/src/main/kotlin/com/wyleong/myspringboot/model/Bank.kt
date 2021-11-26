package com.wyleong.myspringboot.model

import javax.validation.constraints.Min
import javax.validation.constraints.Size

data class Bank(

    val accountNumber: String,

    val trust: Double,

    @field:Min(value = 1, message = "FEE_MUST_BE_POSITIVE")
    val transactionFee: Int,

    @field:Size(min = 3, message = "INVALID_LENGTH")
    val codeName: String = "abc"
)
