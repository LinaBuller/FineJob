package com.buller.finejob.ui.favorite

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.buller.finejob.R
import com.buller.finejob.databinding.FragmentFavoriteBinding
import com.buller.finejob.ui.base.BaseBottomMenuFragment
import com.buller.finejob.ui.CustomListDelegationAdapter
import com.buller.finejob.ui.VacancyAdapterDelegateFactory
import com.buller.finejob.ui.home.NavigateDestination
import com.buller.finejob.ui.home.SharedViewModel
import com.buller.finejob.utils.HorizontalSpacesItemDecorator
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel


class FavoriteFragmentBottom : BaseBottomMenuFragment(), NavigateDestination {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by activityViewModel()
    private lateinit var vacancyListDelegationAdapter: CustomListDelegationAdapter
    private val vacancyAdapterDelegateFactory: VacancyAdapterDelegateFactory by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initFavoriteVacancyList()
        initFavoriteBadge()
        return root
    }

    private fun initFavoriteVacancyList() = with(binding) {

        val favoriteAdapter = vacancyAdapterDelegateFactory.create(
            fragment = this@FavoriteFragmentBottom,
            destination = this@FavoriteFragmentBottom,
            favoriteStateManager = sharedViewModel
        )

        vacancyListDelegationAdapter = CustomListDelegationAdapter(favoriteAdapter)

        val verticalItemDecorator = HorizontalSpacesItemDecorator(16)
        rcVacancy.addItemDecoration(verticalItemDecorator)
        rcVacancy.adapter = vacancyListDelegationAdapter
        val verticalLayoutManager = LinearLayoutManager(context)
        rcVacancy.layoutManager = verticalLayoutManager

        sharedViewModel.vacancyList.observe(viewLifecycleOwner) { vacancies ->
            val favoriteVacancyList = vacancies.filter { it.isFavorite }
            Log.d("TupayaTvar", " Update list from sharedViewModel ${favoriteVacancyList.size}")
            vacancyListDelegationAdapter.updateAdapters(favoriteVacancyList)
        }
    }


    private fun initFavoriteBadge() {
        sharedViewModel.favoriteVacancyCount.observe(viewLifecycleOwner) { count ->
            createFavoriteBadge(count)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getNavigationId(): Int {
        return R.id.action_navigation_favorite_to_vacancyFragment
    }
}