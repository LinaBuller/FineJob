package com.buller.domain.usecases

import com.buller.domain.entities.Vacancy
import com.buller.domain.interfaces.VacancyRepository

class GetVacancyListUseCase(private val vacancyRepository: VacancyRepository) {
    suspend operator fun invoke(): List<Vacancy> {
        return vacancyRepository.getVacanciesList()
    }
}