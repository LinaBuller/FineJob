package com.buller.finejob.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class HorizontalSpacesItemDecorator(private val space: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.top = space
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = 0
        }

        if (parent.getChildAdapterPosition(view) == state.itemCount - 1) {
            outRect.bottom = space
        }
    }
}