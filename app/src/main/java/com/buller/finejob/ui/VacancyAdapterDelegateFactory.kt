package com.buller.finejob.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.buller.domain.entities.DisplayableItem
import com.buller.domain.entities.Vacancy
import com.buller.finejob.R
import com.buller.finejob.ui.home.FavoriteStateManager
import com.buller.finejob.ui.home.HomeFragmentConstants
import com.buller.finejob.ui.home.NavigateDestination
import com.buller.finejob.utils.DataFormatUtil
import com.buller.finejob.utils.FormatTextHelper
import com.buller.finejob.utils.PluralsHelper
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate

class VacancyAdapterDelegateFactory {
    fun create(
        fragment: Fragment,
        destination: NavigateDestination,
        favoriteStateManager: FavoriteStateManager
    ): AdapterDelegate<List<DisplayableItem>> {

        val itemClickedListener = { vacancy: Vacancy ->
            val bundle = Bundle().apply {
                putParcelable(HomeFragmentConstants.VACANCY_ITEM_KEY, vacancy)
            }
            fragment.findNavController().navigate(
                destination.getNavigationId(),
                bundle
            )
        }

        val onFavoriteIconClick = { vacancy: Vacancy ->
            favoriteStateManager.toggleFavorite(vacancy)
        }

        val onResponseButtonClick = { vacancy: Vacancy ->
            //открыть фрагмент отклика
        }

        val setHumansViewsCount = { count: Int ->
            PluralsHelper.getPlurals(fragment.resources, R.plurals.humans_views_count, count)
        }

        val setPublishedDate = {stringDate:String->
            val day = DataFormatUtil.formatPublishedDate(stringDate)
            fragment.resources.getString(R.string.published_date,day)
        }
        return VacancyAdapterDelegate(
            itemClickedListener = itemClickedListener,
            onFavoriteIconClick = onFavoriteIconClick,
            onResponseButtonClick = onResponseButtonClick,
            setHumansViewsCount = setHumansViewsCount,
            setPublishedDate = setPublishedDate
        )
    }
}