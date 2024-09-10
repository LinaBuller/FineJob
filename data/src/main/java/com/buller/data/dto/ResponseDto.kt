package com.buller.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseDto(
    @field:Json(name = "offers")
    val offers: List<OfferDto>,
    @field:Json(name = "vacancies")
    val vacancies: List<VacancyDto>
)