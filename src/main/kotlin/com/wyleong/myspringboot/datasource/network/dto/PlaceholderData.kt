package com.wyleong.myspringboot.datasource.network.dto

data class PlaceholderData(
    val userId: Int,
    val id: Int,
    val title: String,
    val completed: Boolean
)

data class PlaceholderDataList(
    val results: Collection<PlaceholderData>
)
