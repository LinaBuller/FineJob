package com.buller.domain.interfaces

import com.buller.domain.Result
import com.buller.domain.entities.Offer
import com.buller.domain.entities.ResponseInfo
import com.buller.domain.entities.Vacancy
import kotlinx.coroutines.flow.Flow

interface VacancyRepository {
    suspend fun getResponseInfo():Flow<Result<ResponseInfo>>
}