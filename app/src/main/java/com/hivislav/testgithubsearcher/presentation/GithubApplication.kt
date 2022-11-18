package com.hivislav.testgithubsearcher.presentation

import android.app.Application
import com.hivislav.testgithubsearcher.di.DaggerApplicationComponent

class GithubApplication : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}