package com.buller.data.source

import android.util.Log
import com.buller.data.api.VacanciesApiService
import com.buller.data.dto.ResponseDto
import com.buller.data.mappers.toOffersList
import com.buller.data.mappers.toVacanciesList
import com.buller.domain.Result
import com.buller.domain.entities.Offer
import com.buller.domain.entities.Vacancy
import com.buller.domain.interfaces.VacanciesSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class VacanciesSourceImpl(private val apiService: VacanciesApiService) : VacanciesSource {

    override suspend fun getVacanciesList(): Flow<Result<List<Vacancy>>> = flow {
        emit(Result.Loading)
        val data = apiService.getVacanciesAndOffers()
        val listVacancy = data.toVacanciesList()
        emit(Result.Success(data = listVacancy))
    }.catch { e ->
        emit(Result.Error(e))
    }


   override suspend fun getOffersList(): Flow<Result<List<Offer>>> = flow {
        emit(Result.Loading)
        val data  = apiService.getVacanciesAndOffers()
        val listOffer = data.toOffersList()
        emit(Result.Success(data = listOffer))
    }.catch { e ->
        emit(Result.Error(e))
    }
}
