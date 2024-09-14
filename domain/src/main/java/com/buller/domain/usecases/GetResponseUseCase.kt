package com.buller.domain.usecases

import com.buller.domain.Result
import com.buller.domain.entities.Offer
import com.buller.domain.entities.ResponseInfo
import com.buller.domain.interfaces.VacancyRepository
import kotlinx.coroutines.flow.Flow

class GetResponseUseCase(private val vacancyRepository: VacancyRepository) {
    suspend operator fun invoke(): Flow<Result<ResponseInfo>> {
        return vacancyRepository.getResponseInfo()
    }
}