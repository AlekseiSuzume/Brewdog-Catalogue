package com.suzume.brewdogcatalogue.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.suzume.brewdogcatalogue.App
import com.suzume.brewdogcatalogue.R
import com.suzume.brewdogcatalogue.databinding.ActivityFavouriteBinding
import com.suzume.brewdogcatalogue.presentation.adapter.BeerAdapter
import javax.inject.Inject

class FavouriteActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val binding by lazy {
        ActivityFavouriteBinding.inflate(layoutInflater)
    }
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[FavouriteViewModel::class.java]
    }
    private val beerAdapter by lazy {
        BeerAdapter()
    }

    private val component by lazy {
        (application as App).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()
        observeViewModel()
        setupClickListener()
        setupLongClickListener()

    }

    private fun isVerticalOrientation(): Boolean {
        return binding.fragmentContainerViewBeerInfoFavorite == null
    }

    private fun init() {
        binding.rvFavourite.adapter = beerAdapter
    }

    private fun observeViewModel() {
        viewModel.favorites.observe(this) {
            beerAdapter.submitList(it)
        }
    }

    private fun launchFragment(fragment: Fragment){
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view_beer_info_favorite, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun launchActivity(intent: Intent){
        startActivity(intent)
    }

    private fun setupClickListener() {
        beerAdapter.onBeerClickListener = {
            if (isVerticalOrientation()) {
                launchActivity(BeerInfoActivity.newIntent(this, it.id))
            } else {
                launchFragment(BeerInfoFragment.newInstance(it.id))
            }
        }
    }

    private fun setupLongClickListener() {
        beerAdapter.onBeerLongClickListener = {
            if (it.favourite) {
                viewModel.removeFavorite(it.id)
                Toast.makeText(this, "${it.name}\nDelete from favorite", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.addFavorite(it.id)
                Toast.makeText(this, "${it.name}\nAdd to favorite", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {

        fun newIntent(context: Context): Intent =
            Intent(context, FavouriteActivity::class.java)

    }

}