package com.buller.domain.usecases

import com.buller.domain.entities.Offer
import com.buller.domain.interfaces.VacancyRepository

class GetOffersListUseCase(private val vacancyRepository: VacancyRepository) {
    suspend operator fun invoke(): List<Offer> {
        return vacancyRepository.getOffersList()
    }
}