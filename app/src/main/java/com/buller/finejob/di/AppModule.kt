package com.buller.finejob.di

import com.buller.finejob.ui.VacancyAdapterDelegateFactory
import com.buller.finejob.ui.home.SharedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel<SharedViewModel> {
        SharedViewModel(
            getResponseUseCase = get()
        )
    }

    single { VacancyAdapterDelegateFactory() }
}