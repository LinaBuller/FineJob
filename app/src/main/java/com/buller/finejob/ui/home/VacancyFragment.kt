package com.buller.finejob.ui.home

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.buller.domain.entities.Vacancy
import com.buller.finejob.R
import com.buller.finejob.databinding.FragmentVacancyBinding
import com.buller.finejob.ui.home.HomeFragmentConstants
import com.buller.finejob.utils.FormatTextHelper
import com.buller.finejob.utils.PluralsHelper

class VacancyFragment : Fragment() {
    private var _binding: FragmentVacancyBinding? = null
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVacancyBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initActionPart()
        initVacancy()
        return root
    }

    private fun initVacancy() = with(binding) {
        val vacancy = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(
                HomeFragmentConstants.VACANCY_ITEM_KEY,
                Vacancy::class.java
            )
        } else {
            arguments?.getParcelable<Vacancy>(HomeFragmentConstants.VACANCY_ITEM_KEY)
        }
        if (vacancy == null) return@with
        showNameVacancy.text = vacancy.title
        showInfoSalary.text = vacancy.salary.full
        showExperience.text = vacancy.experience.text
        showEmployment.text = FormatTextHelper.makeFormattedStroke(vacancy.schedules)

        var responseCount = vacancy.appliedNumber
        if (responseCount == null) {
            responseCount = 0
        }
        showResponses.text =
            PluralsHelper.getPlurals(resources, R.plurals.responses_count, responseCount)

        var peopleViewsCount = vacancy.lookingNumber
        if (peopleViewsCount == null) {
            peopleViewsCount = 0
        }
        showPeople.text =
            PluralsHelper.getPlurals(resources, R.plurals.humans_views_count, peopleViewsCount)

        showNameCompany.text = vacancy.company

        val addressList =
            listOf(vacancy.address.town, vacancy.address.street, vacancy.address.house)
        showCompanyAddress.text = FormatTextHelper.makeFormattedStroke(addressList)

        showInfoCompany.text = vacancy.description
        showInfoVacancyTasks.text = vacancy.responsibilities
        initQuestsPart()
    }

    private fun initQuestsPart() {
        //TODO сделать recyclerView
    }

    private fun initActionPart() {
        initBackButton()
        initShareButton()
        initHideVacancy()
        setVacancyToFavorites()
    }

    private fun initBackButton() = with(binding) {
        buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initShareButton() {}
    private fun initHideVacancy() {}
    private fun setVacancyToFavorites() {}
}