package com.buller.finejob.ui.home

import com.buller.domain.entities.Vacancy

interface FavoriteStateManager {
    fun toggleFavorite(vacancy: Vacancy)
}