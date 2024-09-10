package com.buller.finejob

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buller.domain.entities.Offer
import com.buller.domain.entities.Vacancy
import com.buller.domain.usecases.GetOffersListUseCase
import com.buller.domain.usecases.GetVacancyListUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    private val getVacanciesListUseCase: GetVacancyListUseCase,
    private val getOffersListUseCase: GetOffersListUseCase
) : ViewModel() {

    private var _vacanciesList: MutableLiveData<List<Vacancy>> = MutableLiveData<List<Vacancy>>(
        emptyList()
    )
    val vacanciesList: LiveData<List<Vacancy>> get() = _vacanciesList

    fun getVacanciesList() {
        viewModelScope.launch {
            _vacanciesList.value = getVacanciesListUseCase.invoke()
        }
    }

    private var _offersList: MutableLiveData<List<Offer>> = MutableLiveData<List<Offer>>()

    val offersList: LiveData<List<Offer>> get() = _offersList

    fun getOffersList() {
        viewModelScope.launch {
            _offersList.value = getOffersListUseCase.invoke()
        }
    }

}