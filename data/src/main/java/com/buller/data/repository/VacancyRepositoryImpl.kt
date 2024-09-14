package com.buller.data.repository

import com.buller.domain.Result
import com.buller.domain.interfaces.VacanciesSource
import com.buller.domain.entities.Offer
import com.buller.domain.entities.ResponseInfo
import com.buller.domain.entities.Vacancy
import com.buller.domain.interfaces.VacancyRepository
import kotlinx.coroutines.flow.Flow

class VacancyRepositoryImpl(private val vacanciesSource: VacanciesSource) : VacancyRepository {
    override suspend fun getResponseInfo(): Flow<Result<ResponseInfo>> {
        return vacanciesSource.getResponseInfo()
    }
}