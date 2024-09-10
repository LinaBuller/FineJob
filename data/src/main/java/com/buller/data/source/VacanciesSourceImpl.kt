package com.buller.data.source

import com.buller.data.api.VacanciesApiService
import com.buller.data.mappers.toOffersList
import com.buller.data.mappers.toVacanciesList
import com.buller.domain.entities.Offer
import com.buller.domain.entities.Vacancy
import com.buller.domain.interfaces.VacanciesSource

class VacanciesSourceImpl(private val apiService: VacanciesApiService): VacanciesSource {

    override suspend fun getVacanciesList(): List<Vacancy> {
        return apiService.getVacanciesAndOffers().toVacanciesList()
        }

    override suspend fun getOffersList(): List<Offer> {
        return apiService.getVacanciesAndOffers().toOffersList()
    }
}
