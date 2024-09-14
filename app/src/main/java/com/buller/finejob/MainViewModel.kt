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

class MainViewModel() : ViewModel() {
}