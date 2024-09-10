package com.buller.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AddressDto(
    @field:Json(name = "house")
    val house: String,
    @field:Json(name = "house")
    val street: String,
    @field:Json(name = "town")
    val town: String
)