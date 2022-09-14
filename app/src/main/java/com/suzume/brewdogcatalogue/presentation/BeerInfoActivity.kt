package com.suzume.brewdogcatalogue.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.suzume.brewdogcatalogue.R
import com.suzume.brewdogcatalogue.databinding.ActivityBeerInfoBinding
import kotlin.properties.Delegates

class BeerInfoActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityBeerInfoBinding.inflate(layoutInflater)
    }

    private var beerId by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        parseIntent()

        if (savedInstanceState == null){
        launchFragment()
        }

    }

    private fun launchFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_view_beer_info, BeerInfoFragment.newInstance(beerId))
            .commit()
    }

    private fun parseIntent() {
        beerId = intent.getIntExtra(EXTRA_FROM_ID, 0)
    }

    companion object {
        private const val EXTRA_FROM_ID = "id"

        fun newIntent(context: Context, fromId: Int): Intent {
            return Intent(context, BeerInfoActivity::class.java)
                .putExtra(EXTRA_FROM_ID, fromId)
        }
    }

}