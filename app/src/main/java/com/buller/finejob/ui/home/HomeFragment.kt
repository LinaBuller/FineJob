package com.buller.finejob.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.buller.domain.entities.DisplayableItem
import com.buller.domain.entities.LoadMoreButton
import com.buller.domain.entities.Offer
import com.buller.finejob.R
import com.buller.finejob.databinding.FragmentHomeBinding
import com.buller.finejob.databinding.ItemLoadMoreBinding
import com.buller.finejob.databinding.ItemOfferBinding
import com.buller.finejob.ui.VacancyAdapterDelegateFactory
import com.buller.finejob.utils.HorizontalSpacesItemDecorator
import com.buller.finejob.utils.PluralsHelper
import com.buller.finejob.utils.VerticalSpacesItemDecorator
import com.buller.finejob.utils.addBadge
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var offersAdapter: ListDelegationAdapter<List<DisplayableItem>>
    private lateinit var vacanciesAdapter: ListDelegationAdapter<List<DisplayableItem>>
    private val homeSharedViewModel: HomeSharedViewModel by activityViewModels()
    private val homeViewModel: HomeViewModel by viewModel()
    private val vacancyAdapterDelegateFactory: VacancyAdapterDelegateFactory by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initFavoriteBadge()
        initOffersPart()
        initVacanciesPart()

        return root
    }

    private fun initOffersPart() = with(binding) {

        offersAdapter = ListDelegationAdapter(offersAdapterDelegate { offer ->
            val url = offer.link
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        })

        val horizontalItemDecoration = VerticalSpacesItemDecorator(8)
        rcOffers.addItemDecoration(horizontalItemDecoration)
        rcOffers.adapter = offersAdapter
        val layoutManagerHorizontal =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rcOffers.layoutManager = layoutManagerHorizontal

        homeViewModel.offersList.observe(viewLifecycleOwner) { offers ->
            updateAdapter(offersAdapter, offers)
        }
    }

    private fun initVacanciesPart() = with(binding) {

        val vacancyAdapter = vacancyAdapterDelegateFactory.create(
            itemClickedListener = { vacancy ->
                val bundle = Bundle().apply {
                    putParcelable(HomeFragmentConstants.VACANCY_ITEM_KEY, vacancy)
                }
                findNavController().navigate(
                    R.id.action_navigation_home_to_vacancyFragment,
                    bundle
                )
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
        //Log.d("TupayaTvar"," Update list to sharedViewModel ${vacancies.size}")

        vacanciesAdapter = ListDelegationAdapter(vacancyAdapter, loadMoreButtonDelegate {
            findNavController().navigate(R.id.action_navigation_home_to_extended_home_fragment)
        })

        val verticalItemDecoration = HorizontalSpacesItemDecorator(16)
        rcVacancy.addItemDecoration(verticalItemDecoration)
        rcVacancy.adapter = vacanciesAdapter
        val layoutManagerVertical = LinearLayoutManager(context)
        rcVacancy.layoutManager = layoutManagerVertical

        homeViewModel.vacanciesList.observe(viewLifecycleOwner) { vacancies ->
            if (vacancies.isNotEmpty()) {
                homeSharedViewModel.setVacancyList(vacancies)
                val vacanciesCount = vacancies.size
                val shortVacancyList =
                    vacancies.subList(0, 3).map { it as DisplayableItem }.toMutableList()
                val suitableList = addLoadMoreButtonToDisplayableItemsList(
                    allItemCount = vacanciesCount, displayableItems = shortVacancyList
                )
                updateAdapter(vacanciesAdapter, suitableList)

            }
        }
    }

    private fun addLoadMoreButtonToDisplayableItemsList(
        allItemCount: Int,
        displayableItems: List<DisplayableItem>,
    ): List<DisplayableItem> {
        val newList = displayableItems.toMutableList()
        val remainingVacancies = allItemCount - newList.size
        val labelButton =
            PluralsHelper.getPlurals(resources, R.plurals.yet_vacancies_count, remainingVacancies)
        val newItem = LoadMoreButton(labelButton)
        newList.add(newItem)
        return newList
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


    private fun offersAdapterDelegate(itemClickedListener: (Offer) -> Unit) =
        adapterDelegateViewBinding<Offer, DisplayableItem, ItemOfferBinding>({ layoutInflater, root ->
            ItemOfferBinding.inflate(layoutInflater, root, false)
        }) {
            bind {
                binding.apply {
                    if (item.id != null) {
                        val iconRes = OffersType.getOfferIcon(item.id!!).iconRes
                        iconForYourOffers.setImageResource(iconRes!!)
                    } else {
                        iconForYourOffers.visibility = View.INVISIBLE
                    }

                    //TODO Если текст длиннее 3-х строк, то обрезается по пробелу

                    if (item.button != null) {
                        textLabelEmployees.maxLines = 2
                        tvUpResume.visibility = View.VISIBLE

                    } else {
                        textLabelEmployees.maxLines = 3
                        tvUpResume.visibility = View.INVISIBLE
                    }
                    textLabelEmployees.text = item.title.trim()
                }
                itemView.setOnClickListener { itemClickedListener(item) }
            }
        }

    private fun loadMoreButtonDelegate(itemClickedListener: () -> Unit) =
        adapterDelegateViewBinding<LoadMoreButton, DisplayableItem, ItemLoadMoreBinding>({ layoutInflater, root ->
            ItemLoadMoreBinding.inflate(layoutInflater, root, false)
        }) {
            bind {
                binding.apply {
                    btMoreVacancies.text = item.label
                    btMoreVacancies.setOnClickListener { itemClickedListener() }
                }
            }
        }

    private fun initFavoriteBadge() {
        homeViewModel.favoriteVacanciesCount.observe(viewLifecycleOwner) { count ->
            createFavoriteBadge(count = count)
        }
    }

    private fun createFavoriteBadge(count: Int) {
        if (count == 0) return
        val bottomNavigationView =
            requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigationView.addBadge(itemId = R.id.navigation_favorite, count = count)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}