package com.buller.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SalaryDto(
    @field:Json(name = "full")
    val full: String,
    @field:Json(name = "short")
    val short: String
)