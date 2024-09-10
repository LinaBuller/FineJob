package com.buller.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VacancyDto(
    @field:Json(name = "id")
    val id: String,
    @field:Json(name = "lookingNumber")
    val lookingNumber: Int,
    @field:Json(name = "title")
    val title: String,
    @field:Json(name = "address")
    val address: AddressDto,
    @field:Json(name = "company")
    val company: String,
    @field:Json(name = "experience")
    val experience: ExperienceDto,
    @field:Json(name = "publishedDate")
    val publishedDate: String,
    @field:Json(name = "isFavorite")
    val isFavorite: Boolean,
    @field:Json(name = "salary")
    val salary: SalaryDto,
    @field:Json(name = "schedules")
    val schedules: List<String>,
    @field:Json(name = "appliedNumber")
    val appliedNumber: Int,
    @field:Json(name = "description")
    val description: String,
    @field:Json(name = "responsibilities")
    val responsibilities: String,
    @field:Json(name = "questions")
    val questions: List<String>
)