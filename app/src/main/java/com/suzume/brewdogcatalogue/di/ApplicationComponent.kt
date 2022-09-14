package com.suzume.brewdogcatalogue.di

import android.app.Application
import com.suzume.brewdogcatalogue.presentation.BeerInfoFragment
import com.suzume.brewdogcatalogue.presentation.FavouriteActivity
import com.suzume.brewdogcatalogue.presentation.MainActivity
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun inject(activity: MainActivity)

    fun inject(activity: FavouriteActivity)

    fun inject(fragment: BeerInfoFragment)

    @Component.Factory
    interface ApplicationComponentFactory {

        fun create(
            @BindsInstance application: Application,
        ): ApplicationComponent

    }

}