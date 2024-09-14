package com.buller.finejob.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buller.domain.Result
import com.buller.domain.entities.Offer
import com.buller.domain.entities.Vacancy
import com.buller.domain.usecases.GetResponseUseCase
import kotlinx.coroutines.launch

class SharedViewModel(private val getResponseUseCase: GetResponseUseCase) : ViewModel(),
    FavoriteStateManager {

    private var _vacancyList: MutableLiveData<List<Vacancy>> = MutableLiveData(emptyList())
    val vacancyList: LiveData<List<Vacancy>> get() = _vacancyList

    private var _offerList: MutableLiveData<List<Offer>> = MutableLiveData(emptyList())
    val offerList: LiveData<List<Offer>> get() = _offerList

    init {
        getResponseInfo()
    }

    private fun getResponseInfo() {
        viewModelScope.launch {
            val result = getResponseUseCase.invoke()
            result.collect { res ->
                when (res) {
                    is Result.Success -> {
                        val responseInfo = res.data
                        val vacancies = responseInfo.vacancyList
                        _vacancyList.value = vacancies
                        _offerList.value = responseInfo.offerList
                        _favoriteVacancyCount.value = getFavoriteVacancyCount(vacancies)
                    }

                    is Result.Error -> {
                        Log.e("TupayaTvar", "Ошибка: ${res.exception}")
                    }

                    is Result.Loading -> {
                        // тут можно показать индикатор загрузки
                    }
                }
            }
        }
    }


    private val _favoriteVacancyCount = MutableLiveData<Int>(0)
    val favoriteVacancyCount: LiveData<Int> get() = _favoriteVacancyCount

    private fun getFavoriteVacancyCount(vacancies: List<Vacancy>): Int {
        return vacancies.filter { it.isFavorite }.size
    }

    private fun addToFavorites() {
        _favoriteVacancyCount.value = (_favoriteVacancyCount.value ?: 0) + 1
    }

    private fun removeFromFavorites() {
        _favoriteVacancyCount.value = (_favoriteVacancyCount.value ?: 0) - 1
    }

    override fun toggleFavorite(vacancy: Vacancy) {
        val updatedVacancy = vacancy.copy(isFavorite = !vacancy.isFavorite)
        _vacancyList.value = _vacancyList.value?.map {
            if (it.id == updatedVacancy.id) updatedVacancy else it
        }
        if (updatedVacancy.isFavorite) {
            addToFavorites()
        } else {
            removeFromFavorites()
        }
    }

}