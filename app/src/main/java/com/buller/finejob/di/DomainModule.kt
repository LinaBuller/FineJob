package com.buller.finejob.di

import com.buller.domain.usecases.GetResponseUseCase
import org.koin.dsl.module

val domainModule = module {
    factory<GetResponseUseCase> {
        GetResponseUseCase(vacancyRepository = get())
    }
}