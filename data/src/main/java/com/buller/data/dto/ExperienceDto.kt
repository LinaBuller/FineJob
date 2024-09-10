package com.buller.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ExperienceDto(
    @field:Json(name = "previewText")
    val previewText: String,
    @field:Json(name = "text")
    val text: String
)