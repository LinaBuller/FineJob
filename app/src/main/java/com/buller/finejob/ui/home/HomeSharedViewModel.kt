package com.buller.finejob.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.buller.domain.entities.Vacancy

class HomeSharedViewModel: ViewModel() {

    private var _vacanciesList: MutableLiveData<List<Vacancy>> = MutableLiveData<List<Vacancy>>(
        emptyList()
    )
    val vacanciesList: LiveData<List<Vacancy>> get() = _vacanciesList

    fun setVacancyList(list: List<Vacancy>) {
        _vacanciesList.value = list
    }

}