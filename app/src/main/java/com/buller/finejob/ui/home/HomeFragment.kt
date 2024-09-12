package com.buller.finejob.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.buller.domain.entities.DisplayableItem
import com.buller.domain.entities.Offer
import com.buller.domain.entities.Vacancy
import com.buller.finejob.databinding.FragmentHomeBinding
import com.buller.finejob.databinding.ItemOfferBinding
import com.buller.finejob.databinding.ItemVacancyBinding
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        homeViewModel.text.observe(viewLifecycleOwner) {

        }

        val adapter = ListDelegationAdapter<List<DisplayableItem>>(
            offersAdapterDelegate {
                val url = it.link
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }


        )
        return root
    }


    private fun offersAdapterDelegate(itemClickedListener: (Offer) -> Unit) =
        adapterDelegateViewBinding<Offer, DisplayableItem, ItemOfferBinding>(
            { layoutInflater, root -> ItemOfferBinding.inflate(layoutInflater, root, false) }
        ) {
            bind {
                val iconRes = OffersType.getOfferIcon(item.id).iconRes
                if (iconRes != null) {
                    binding.iconForYourOffers.setImageResource(iconRes)
                } else {
                    binding.iconForYourOffers.visibility = View.GONE
                }
                //TODO Если текст длиннее 3-х строк, то обрезается по пробелу
                binding.tvUpResume.visibility = if (item.button.text.isBlank()) {
                    binding.textLabelEmployees.maxLines = 2
                    View.VISIBLE
                } else {
                    binding.textLabelEmployees.maxLines = 3
                    View.GONE
                }
                binding.textLabelEmployees.text = item.title
            }
        }

    private fun vacancyAdapterDelegate(itemClickedListener: (Vacancy) -> Unit) =
        adapterDelegateViewBinding<Vacancy, DisplayableItem, ItemVacancyBinding>(
            { layoutInflater, root -> ItemVacancyBinding.inflate(layoutInflater, root, false) }
        ) {
            bind {
                binding.
                binding.experience.text = item.experience.text
            }
        }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}