package com.buller.finejob.di

import com.buller.finejob.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel {
        MainViewModel(
            getOffersListUseCase = get(),
            getVacanciesListUseCase = get()
        )
    }
}