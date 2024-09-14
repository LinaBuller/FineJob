package com.buller.domain.usecases

import com.buller.domain.Result
import com.buller.domain.entities.Vacancy
import com.buller.domain.interfaces.VacancyRepository
import kotlinx.coroutines.flow.Flow

class GetVacancyListUseCase(private val vacancyRepository: VacancyRepository) {
    suspend operator fun invoke(): Flow<Result<List<Vacancy>>> {
        return vacancyRepository.getVacanciesList()
    }
}