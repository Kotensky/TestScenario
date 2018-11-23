package com.kotensky.testscenario.application

import android.app.Application
import com.kotensky.testscenario.di.components.ApplicationComponent
import com.kotensky.testscenario.di.components.DaggerApplicationComponent
import com.kotensky.testscenario.di.modules.ApplicationModule

class TestScenarioApplication : Application() {

    var applicationComponent: ApplicationComponent? = null
        private set

    override fun onCreate() {
        super.onCreate()
        initAppComponent()
    }

    private fun initAppComponent() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }
}