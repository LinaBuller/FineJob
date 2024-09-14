package com.buller.finejob.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.buller.domain.entities.DisplayableItem
import com.buller.domain.entities.Vacancy
import com.buller.finejob.databinding.ItemVacancyBinding
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate

class VacancyAdapterDelegate(
    private val itemClickedListener: (Vacancy) -> Unit,
    private val onFavoriteIconClick: (Vacancy) -> Unit,
    private val onResponseButtonClick: (Vacancy) -> Unit,
    private val setHumansViewsCount:(Int)->String
) : AdapterDelegate<List<DisplayableItem>>() {
    override fun isForViewType(items: List<DisplayableItem>, position: Int): Boolean {
        return items[position] is Vacancy
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val binding = ItemVacancyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VacancyViewHolder(binding)
    }

    override fun onBindViewHolder(
        items: List<DisplayableItem>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        val item = items[position] as Vacancy
        (holder as VacancyViewHolder).bind(item)
    }

    inner class VacancyViewHolder(private val binding: ItemVacancyBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Vacancy) {
            with(binding) {
                val count = item.lookingNumber
                if (count != null) {
                    textInfoSearchEmployees.text = setHumansViewsCount.invoke(count)
                }
                nameVacancy.text = item.title

                if (item.salary.short == null) {
                    moneyVacancy.visibility = View.GONE
                } else {
                    moneyVacancy.text = item.salary.short
                }
                companyName.text = item.company
                townCompanyName.text = item.address.town

                experience.text = item.experience.text
                icon.isSelected = item.isFavorite
                //TODO исправить в правильную строку, приходит  "publishedDate": "2024-02-28",
                publishTime.text = item.publishedDate
                icon.setOnClickListener { onFavoriteIconClick(item) }
                btResponse.setOnClickListener { onResponseButtonClick(item) }
                itemView.setOnClickListener { itemClickedListener(item) }
            }
        }
    }
}