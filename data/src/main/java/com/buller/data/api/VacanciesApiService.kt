package com.buller.data.api


import com.buller.data.dto.ResponseDto
import retrofit2.http.GET

interface VacanciesApiService {
    @GET(ApiConstants.JSON_PATH)
    suspend fun getVacanciesAndOffers(): ResponseDto
}