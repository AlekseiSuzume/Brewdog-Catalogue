package com.suzume.brewdogcatalogue.presentation

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.suzume.brewdogcatalogue.App
import com.suzume.brewdogcatalogue.R
import com.suzume.brewdogcatalogue.databinding.ActivityMainBinding
import com.suzume.brewdogcatalogue.presentation.adapter.BeerAdapter
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
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
        setupClickListener()
        setupLongClickListener()
        setupReachEndListener()
        observeViewModel()

    }

    private fun init() {
        viewModel.loadData()
        binding.recyclerView.adapter = beerAdapter
    }

    private fun observeViewModel() {
        viewModel.beerList.observe(this) {
            beerAdapter.submitList(it)
        }
    }

    private fun isVerticalOrientation(): Boolean {
        return binding.fragmentContainerViewBeerInfo == null
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_view_beer_info, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun launchActivity(intent: Intent) {
        startActivity(intent)
    }

    private fun setupClickListener() {
        beerAdapter.onBeerClickListener = {
            if (isVerticalOrientation()) {
                launchActivity(BeerInfoActivity.newIntent(this@MainActivity, it.id))
            } else {
                launchFragment(BeerInfoFragment.newInstance(it.id))
            }
        }
    }

    private fun setupLongClickListener() {
        beerAdapter.onBeerLongClickListener = {
            if (it.favourite) {
                viewModel.removeFavorite(it.id)
                Toast.makeText(this, "${it.name}\nDelete from favorite", Toast.LENGTH_SHORT)
                    .show()
            } else {
                viewModel.addFavorite(it.id)
                Toast.makeText(this, "${it.name}\nAdd to favorite", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupReachEndListener() {
        beerAdapter.onReachEndListener = {
            viewModel.loadData(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.favouriteMenuItem) {
            val intent = FavouriteActivity.newIntent(this)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}