package com.buller.finejob.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.buller.domain.entities.DisplayableItem
import com.buller.domain.entities.LoadMoreButton
import com.buller.domain.entities.Offer
import com.buller.finejob.R
import com.buller.finejob.databinding.FragmentHomeBinding
import com.buller.finejob.databinding.ItemLoadMoreBinding
import com.buller.finejob.databinding.ItemOfferBinding
import com.buller.finejob.ui.base.BaseBottomMenuFragment
import com.buller.finejob.ui.CustomListDelegationAdapter
import com.buller.finejob.ui.VacancyAdapterDelegateFactory
import com.buller.finejob.utils.HorizontalSpacesItemDecorator
import com.buller.finejob.utils.PluralsHelper
import com.buller.finejob.utils.VerticalSpacesItemDecorator
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import org.koin.android.ext.android.inject

class HomeFragment : BaseBottomMenuFragment(), NavigateDestination {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var offerListDelegationAdapter: CustomListDelegationAdapter
    private lateinit var vacancyListDelegationAdapter: CustomListDelegationAdapter
    private val vacancyAdapterDelegateFactory: VacancyAdapterDelegateFactory by inject()
    private val sharedViewModel: SharedViewModel by activityViewModel()

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

        val offerAdapter = offersAdapterDelegate { offer ->
            val url = offer.link
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
        offerListDelegationAdapter = CustomListDelegationAdapter(offerAdapter)

        val horizontalItemDecorator = VerticalSpacesItemDecorator(8)
        rcOffers.addItemDecoration(horizontalItemDecorator)
        rcOffers.adapter = offerListDelegationAdapter
        val layoutManagerHorizontal =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rcOffers.layoutManager = layoutManagerHorizontal

        sharedViewModel.offerList.observe(viewLifecycleOwner) { offers ->
            offerListDelegationAdapter.updateAdapters(offers)
        }
    }

    private fun initVacanciesPart() = with(binding) {

        val vacancyAdapter = vacancyAdapterDelegateFactory.create(
            fragment = this@HomeFragment,
            destination = this@HomeFragment,
            favoriteStateManager = sharedViewModel
        )

        vacancyListDelegationAdapter =
            CustomListDelegationAdapter(vacancyAdapter, loadMoreButtonDelegate {
                findNavController().navigate(R.id.action_navigation_home_to_extended_home_fragment)
            })

        val verticalItemDecorator = HorizontalSpacesItemDecorator(16)
        rcVacancy.addItemDecoration(verticalItemDecorator)
        rcVacancy.adapter = vacancyListDelegationAdapter
        val verticalLayoutManager = LinearLayoutManager(context)
        rcVacancy.layoutManager = verticalLayoutManager

        sharedViewModel.vacancyList.observe(viewLifecycleOwner) { vacancies ->
            if (vacancies.isNotEmpty()) {
                val vacanciesCount = vacancies.size
                val shortVacancyList =
                    vacancies.subList(0, 3).map { it as DisplayableItem }.toMutableList()
                val suitableList = addLoadMoreButtonToDisplayableItemsList(
                    allItemCount = vacanciesCount, displayableItems = shortVacancyList
                )
                vacancyListDelegationAdapter.updateAdapters(suitableList)
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
                    if (item.button == null || item.button!!.text == null) {
                        textLabelEmployees.maxLines = 3
                        tvUpResume.visibility = View.GONE
                    } else {
                        textLabelEmployees.maxLines = 2
                        tvUpResume.visibility = View.VISIBLE
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
        sharedViewModel.favoriteVacancyCount.observe(viewLifecycleOwner) { count ->
            setFavoriteBadge(count = count)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getNavigationId(): Int {
        return R.id.action_navigation_home_to_vacancyFragment
    }
}