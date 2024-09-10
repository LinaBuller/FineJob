package com.buller.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ButtonDto(
    @field:Json(name = "text")
    val text: String
)