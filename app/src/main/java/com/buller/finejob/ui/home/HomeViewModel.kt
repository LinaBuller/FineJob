package com.buller.finejob.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buller.domain.entities.Offer
import com.buller.domain.entities.Vacancy
import com.buller.domain.usecases.GetOffersListUseCase
import com.buller.domain.usecases.GetVacancyListUseCase
import kotlinx.coroutines.launch
import com.buller.domain.Result

class HomeViewModel(
    private val getVacanciesListUseCase: GetVacancyListUseCase,
    private val getOffersListUseCase: GetOffersListUseCase
) : ViewModel() {
    private var _vacanciesList: MutableLiveData<List<Vacancy>> = MutableLiveData(emptyList())
    val vacanciesList: LiveData<List<Vacancy>> get() = _vacanciesList

    init {
        getOffersList()
        getVacanciesList()
    }

    private val _favoriteVacanciesCount = MutableLiveData<Int>(0)
    val favoriteVacanciesCount: LiveData<Int> get() = _favoriteVacanciesCount

    private fun getVacanciesList() {
        viewModelScope.launch {
            val result = getVacanciesListUseCase.invoke()
            result.collect { res ->
                when (res) {
                    is Result.Success -> {
                        val vacancies = res.data
                        _vacanciesList.value = vacancies
                        _favoriteVacanciesCount.value = getFavoriteVacanciesCount(vacancies)
                    }

                    is Result.Error -> {
                        Log.e("TupayaTvar", "Ошибка: ${res.exception}")
                    }

                    is Result.Loading -> {
                        //TODO если останется время, можно сделать показ загрузки
                    }
                }
            }
        }
    }

    private var _offersList: MutableLiveData<List<Offer>> = MutableLiveData(emptyList())

    val offersList: LiveData<List<Offer>> get() = _offersList

    private fun getOffersList() {
        viewModelScope.launch {
            val result = getOffersListUseCase.invoke()
            result.collect { res ->
                when(res){
                    is Result.Success ->{
                        _offersList.value = res.data
                    }
                    is Result.Error -> {
                        Log.e("TupayaTvar", "Ошибка: ${res.exception}")
                    }

                    is Result.Loading -> {
                        //TODO если останется время, можно сделать показ загрузки
                    }
                }
            }
        }
    }

    private fun getFavoriteVacanciesCount(vacancies: List<Vacancy>): Int {
        return vacancies.filter { it.isFavorite }.size
    }

    fun addToFavorites() {
        // добавление в избранное
        //_favoriteCount.value = (_favoriteCount.value ?: 0) + 1
    }

    fun removeFromFavorites() {
        // удаление из избранного
        //_favoriteCount.value = (_favoriteCount.value ?: 0) - 1
    }
}