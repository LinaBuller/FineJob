package com.buller.data.source

import com.buller.data.api.VacanciesApiService
import com.buller.data.mappers.toResponseInfo
import com.buller.domain.Result
import com.buller.domain.entities.ResponseInfo
import com.buller.domain.interfaces.VacanciesSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class VacanciesSourceImpl(private val apiService: VacanciesApiService) : VacanciesSource {

    override suspend fun getResponseInfo(): Flow<Result<ResponseInfo>>  = flow {
        emit(Result.Loading)
        val data = apiService.getVacanciesAndOffers()
        val responseInfo = data.toResponseInfo()
        emit(Result.Success(data = responseInfo))
    }.catch { e ->
        emit(Result.Error(e))
    }
}
