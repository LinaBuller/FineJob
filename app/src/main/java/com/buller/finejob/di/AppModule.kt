package com.buller.finejob.di

import com.buller.finejob.ui.VacancyAdapterDelegateFactory
import com.buller.finejob.ui.favorite.FavoriteViewModel
import com.buller.finejob.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel<HomeViewModel> {
        HomeViewModel(
            getOffersListUseCase = get(),
            getVacanciesListUseCase = get()
        )
    }

    viewModel {
        FavoriteViewModel()
    }

    single { VacancyAdapterDelegateFactory() }

}