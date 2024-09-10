package com.buller.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OfferDto(
    @field:Json(name = "button")
    val button: ButtonDto,
    @field:Json(name = "id")
    val id: String,
    @field:Json(name = "link")
    val link: String,
    @field:Json(name = "title")
    val title: String
)