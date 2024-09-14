package com.buller.finejob.ui.base

import android.graphics.Color
import androidx.fragment.app.Fragment
import com.buller.finejob.R
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView

open class BaseBottomMenuFragment : Fragment() {

    fun setFavoriteBadge(count: Int) {
        val bottomNavigationView =
            requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
        val badge = bottomNavigationView.getOrCreateBadge(R.id.navigation_favorite)
        changeCount(badge = badge, count = count)
    }

    private fun changeCount(badge: BadgeDrawable, count: Int) {
        if (count == 0) {
            badge.isVisible = false
        } else {
            badge.isVisible = true
        }
        badge.number = count
        badge.backgroundColor = Color.RED
        badge.badgeTextColor = Color.WHITE
        badge.horizontalOffset = 10
        badge.verticalOffset = 20
    }
}