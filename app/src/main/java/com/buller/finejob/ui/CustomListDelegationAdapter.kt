package com.buller.finejob.ui

import com.buller.domain.entities.DisplayableItem
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

class CustomListDelegationAdapter(
    vararg delegates: AdapterDelegate<List<DisplayableItem>>
) : ListDelegationAdapter<List<DisplayableItem>>() {

    init {
        delegates.forEach { delegatesManager.addDelegate(it) }
    }

    fun updateAdapters(
        vararg items: List<DisplayableItem>
    ) {
        if (items.isNotEmpty()) {
            val list = mutableListOf<DisplayableItem>()
            items.forEach { displayableItems ->
                list.addAll(displayableItems)
            }
            this.items = list
            //можно добавить diffutil
            notifyDataSetChanged()
        }
    }
}