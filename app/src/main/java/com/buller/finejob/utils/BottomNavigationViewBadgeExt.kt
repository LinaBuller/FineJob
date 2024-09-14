package com.buller.finejob.utils

import android.graphics.Color
import com.google.android.material.bottomnavigation.BottomNavigationView

fun BottomNavigationView.addBadge(itemId: Int, count: Int) {
    val badge = getOrCreateBadge(itemId)
    badge.number = count
    badge.isVisible = true
    badge.backgroundColor = Color.RED
    badge.badgeTextColor = Color.WHITE
    badge.horizontalOffset = 10
    badge.verticalOffset = 20
}