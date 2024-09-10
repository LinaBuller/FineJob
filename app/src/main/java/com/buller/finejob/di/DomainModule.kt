package com.buller.finejob.di

import com.buller.domain.usecases.GetOffersListUseCase
import com.buller.domain.usecases.GetVacancyListUseCase
import org.koin.dsl.module

val domainModule = module {
    factory<GetOffersListUseCase> {
        GetOffersListUseCase(vacancyRepository = get())
    }

    factory<GetVacancyListUseCase> {
        GetVacancyListUseCase(vacancyRepository = get())
    }
}