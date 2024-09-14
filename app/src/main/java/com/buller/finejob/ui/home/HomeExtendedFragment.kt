package com.buller.finejob.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.buller.finejob.R
import com.buller.finejob.databinding.FragmentExtendedHomeBinding
import com.buller.finejob.ui.base.BaseBottomMenuFragment
import com.buller.finejob.ui.CustomListDelegationAdapter
import com.buller.finejob.ui.VacancyAdapterDelegateFactory
import com.buller.finejob.utils.HorizontalSpacesItemDecorator
import com.buller.finejob.utils.PluralsHelper
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel


class HomeExtendedFragment : BaseBottomMenuFragment(), NavigateDestination {
    private var _binding: FragmentExtendedHomeBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by activityViewModel()
    private lateinit var vacancyListDelegationAdapter: CustomListDelegationAdapter
    private val vacancyAdapterDelegateFactory: VacancyAdapterDelegateFactory by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExtendedHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initVacanciesPart()
        initBackButton()
        initFavoriteBadge()

        return root
    }


    private fun initFavoriteBadge() {
        sharedViewModel.favoriteVacancyCount.observe(viewLifecycleOwner) { count ->
            setFavoriteBadge(count = count)
        }
    }

    private fun initVacanciesPart() {
        val vacancyAdapter = vacancyAdapterDelegateFactory.create(
            fragment = this@HomeExtendedFragment,
            destination = this,
            sharedViewModel

        )
        vacancyListDelegationAdapter = CustomListDelegationAdapter(vacancyAdapter)

        binding.apply {
            val verticalItemDecorator = HorizontalSpacesItemDecorator(16)
            rcVacancy.addItemDecoration(verticalItemDecorator)
            rcVacancy.adapter = vacancyListDelegationAdapter
            val verticalLayoutManager = LinearLayoutManager(context)
            rcVacancy.layoutManager = verticalLayoutManager
        }

        sharedViewModel.vacancyList.observe(viewLifecycleOwner) { vacancies ->
            if (vacancies.isNotEmpty()) {
                updateVacanciesCountLabel(vacancies.size)
                vacancyListDelegationAdapter.updateAdapters(vacancies)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getNavigationId(): Int {
        return R.id.action_extended_home_fragment_to_vacancyFragment
    }
}