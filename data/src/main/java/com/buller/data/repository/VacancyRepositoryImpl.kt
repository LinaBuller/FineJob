package com.buller.data.repository

import com.buller.domain.interfaces.VacanciesSource
import com.buller.domain.entities.Offer
import com.buller.domain.entities.Vacancy
import com.buller.domain.interfaces.VacancyRepository

class VacancyRepositoryImpl(private val vacanciesSource: VacanciesSource) : VacancyRepository {

    override suspend fun getVacanciesList(): List<Vacancy> {
        return vacanciesSource.getVacanciesList()
    }

    override suspend fun getOffersList(): List<Offer> {
        return vacanciesSource.getOffersList()
    }
}