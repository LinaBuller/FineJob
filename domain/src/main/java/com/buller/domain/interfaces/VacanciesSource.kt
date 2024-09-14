package com.buller.domain.interfaces

import com.buller.domain.Result
import com.buller.domain.entities.Offer
import com.buller.domain.entities.Vacancy
import kotlinx.coroutines.flow.Flow

interface VacanciesSource {
    suspend fun getVacanciesList(): Flow<Result<List<Vacancy>>>
    suspend fun getOffersList(): Flow<Result<List<Offer>>>
}