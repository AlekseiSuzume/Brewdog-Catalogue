package com.suzume.brewdogcatalogue

import android.app.Application
import com.suzume.brewdogcatalogue.di.DaggerApplicationComponent

class App : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

}
