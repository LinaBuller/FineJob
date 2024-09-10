package com.buller.domain.interfaces

import com.buller.domain.entities.Offer
import com.buller.domain.entities.Vacancy

interface VacancyRepository {
    suspend fun getVacanciesList(): List<Vacancy>
    suspend fun getOffersList(): List<Offer>
}