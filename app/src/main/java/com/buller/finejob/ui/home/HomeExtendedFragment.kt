package com.buller.finejob.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.buller.domain.entities.DisplayableItem
import com.buller.finejob.R
import com.buller.finejob.databinding.FragmentExtendedHomeBinding
import com.buller.finejob.ui.VacancyAdapterDelegateFactory
import com.buller.finejob.utils.HorizontalSpacesItemDecorator
import com.buller.finejob.utils.PluralsHelper
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import org.koin.android.ext.android.inject


class HomeExtendedFragment : Fragment() {
    private var _binding: FragmentExtendedHomeBinding? = null
    private val binding get() = _binding!!
    private val homeSharedViewModel: HomeSharedViewModel by activityViewModels()
    private lateinit var vacanciesAdapter: ListDelegationAdapter<List<DisplayableItem>>
    private val vacancyAdapterDelegateFactory: VacancyAdapterDelegateFactory by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExtendedHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initVacanciesPart()
        initBackButton()
        return root
    }

    private fun initVacanciesPart() {
        val extendedAdapter = vacancyAdapterDelegateFactory.create(
            itemClickedListener = {
                //TODO открытие полного описания вакансии
            },
            onResponseButtonClick = {
                //TODO вызов окна с возможностью отклика
            },
            onFavoriteIconClick = {
                //TODO смена избранное/не избранное
            },
            setHumansViewsCount = { count ->
                PluralsHelper.getPlurals(resources, R.plurals.humans_views_count, count)
            })

        vacanciesAdapter = ListDelegationAdapter(extendedAdapter)

        binding.apply {
            val verticalItemDecoration = HorizontalSpacesItemDecorator(16)
            rcVacancy.addItemDecoration(verticalItemDecoration)
            rcVacancy.adapter = vacanciesAdapter
            val layoutManagerVertical = LinearLayoutManager(context)
            rcVacancy.layoutManager = layoutManagerVertical
        }

        homeSharedViewModel.vacanciesList.observe(viewLifecycleOwner) { list ->
            if (list.isNotEmpty()) {
                updateVacanciesCountLabel(list.size)
                updateAdapter(vacanciesAdapter, list)
            }
        }
    }

    private fun updateVacanciesCountLabel(count: Int) {
        val text = PluralsHelper.getPlurals(resources, R.plurals.vacancies_count, count)
        binding.vacanciesCount.text = text
    }

    private fun initBackButton() {
        binding.btBackOnHome.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun updateAdapter(
        adapter: ListDelegationAdapter<List<DisplayableItem>>, vararg items: List<DisplayableItem>
    ) {
        val list = mutableListOf<DisplayableItem>()
        items.forEach { displayableItems ->
            list.addAll(displayableItems)
        }
        adapter.items = list
        //TODO добавить DIFFUTIl
        adapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}