package com.buller.finejob.ui

import com.buller.domain.entities.DisplayableItem
import com.buller.domain.entities.Vacancy
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate

class VacancyAdapterDelegateFactory {
    fun create(
        itemClickedListener: (Vacancy) -> Unit,
        onFavoriteIconClick: (Vacancy) -> Unit,
        onResponseButtonClick: (Vacancy) -> Unit,
        setHumansViewsCount: (Int) -> String
    ): AdapterDelegate<List<DisplayableItem>> {
        return VacancyAdapterDelegate(
            itemClickedListener,
            onFavoriteIconClick,
            onResponseButtonClick,
            setHumansViewsCount
        )
    }
}