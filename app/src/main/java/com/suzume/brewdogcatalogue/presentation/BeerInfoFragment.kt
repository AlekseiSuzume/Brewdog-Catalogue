package com.suzume.brewdogcatalogue.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.suzume.brewdogcatalogue.App
import com.suzume.brewdogcatalogue.R
import com.suzume.brewdogcatalogue.databinding.FragmentBeerInfoBinding
import com.suzume.brewdogcatalogue.domain.entity.BeerInfoEntity
import javax.inject.Inject
import kotlin.properties.Delegates

class BeerInfoFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (requireActivity().application as App).component
    }

    private var _binding: FragmentBeerInfoBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentBeerInfoBinding == null")

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[BeerInfoViewModel::class.java]
    }

    private var beerId by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        parseArguments()
    }

    private fun parseArguments() {
        arguments?.let {
            beerId = it.getInt(ARG_BEER_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentBeerInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.getBeerInfo(beerId).observe(viewLifecycleOwner) {
            with(binding) {
                with(it) {
                    tvName.text = name
                    tvTag.text = tagLine
                    tvAbv.text =
                        root.resources.getString(R.string.abv, abv)
                    tvIbu.text =
                        root.resources.getString(R.string.ibu, ibu)
                    tvOg.text = targetOg
                    tvHops.text = hops
                    tvMalts.text = malts
                    if (imageUrl.isNotEmpty()) {
                        Glide
                            .with(binding.root.context)
                            .load(imageUrl)
                            .into(ivBeer)
                    }
                    if (favourite) {
                        ivFavourite.setImageResource(R.drawable.beerglasson)
                    } else {
                        ivFavourite.setImageResource(R.drawable.beerglassoff)
                    }
                    setupClickListener(it)
                }
            }
        }
    }

    private fun setupClickListener(beerInfoEntity: BeerInfoEntity) {
        binding.btnFavourite.setOnClickListener {
            if (beerInfoEntity.favourite) {
                viewModel.removeFavorite(beerInfoEntity.id)
                Toast.makeText(requireActivity(),
                    "${beerInfoEntity.name}\nDelete from favorite",
                    Toast.LENGTH_SHORT)
                    .show()
            } else {
                viewModel.addFavorite(beerInfoEntity.id)
                Toast.makeText(requireActivity(),
                    "${beerInfoEntity.name}\nAdd to favorite",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {

        private const val ARG_BEER_ID = "beer_id"

        fun newInstance(beerId: Int) =
            BeerInfoFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_BEER_ID, beerId)
                }
            }
    }
}