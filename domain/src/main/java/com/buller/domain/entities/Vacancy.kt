package com.buller.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class Vacancy(
    val id: String,
    val lookingNumber: Int?,
    val title: String,
    val address: Address,
    val company: String,
    val experience: Experience,
    val publishedDate: String,
    var isFavorite: Boolean,
    val salary: Salary,
    val schedules: List<String>,
    val appliedNumber: Int?,
    val description: String?,
    val responsibilities: String,
    val questions: List<String>
): DisplayableItem,Parcelable