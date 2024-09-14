package com.buller.finejob.ui.home

import androidx.annotation.DrawableRes
import com.buller.finejob.R

sealed class OffersType(@DrawableRes val iconRes: Int?) {
    data object NearVacancies : OffersType(iconRes = R.drawable.ic_type_nav_state_default)
    data object LevelUpResume : OffersType(iconRes = R.drawable.ic_type_star_state_default)
    data object TemporaryJob : OffersType(iconRes = R.drawable.ic_type_doc_state_default)
    data object BlankIcon : OffersType(iconRes = null)

    companion object {
        fun getOfferIcon(idOffer: String): OffersType {
            return when (idOffer) {
                "near_vacancies" -> NearVacancies
                "level_up_resume" -> LevelUpResume
                "temporary_job" -> TemporaryJob
                else -> BlankIcon
            }
        }
    }
}
