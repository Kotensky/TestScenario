package com.kotensky.testscenario.di.modules

import android.content.Context
import com.kotensky.testscenario.application.TestScenarioApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(val context: TestScenarioApplication) {

    @Provides
    @Singleton
    fun provideApplicationContext(): Context = context

}