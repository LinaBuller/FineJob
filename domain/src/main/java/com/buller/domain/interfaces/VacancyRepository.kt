package com.buller.domain.interfaces

import com.buller.domain.Result
import com.buller.domain.entities.Offer
import com.buller.domain.entities.Vacancy
import kotlinx.coroutines.flow.Flow

interface VacancyRepository {
    suspend fun getVacanciesList():Flow<Result<List<Vacancy>>>
    suspend fun getOffersList(): Flow<Result<List<Offer>>>
}