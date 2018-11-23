package com.kotensky.testscenario.di.components

import android.content.Context
import com.kotensky.testscenario.di.modules.ApplicationModule
import com.kotensky.testscenario.di.modules.RoomModule
import com.kotensky.testscenario.managers.SharedPreferencesManager
import com.kotensky.testscenario.model.room.dao.ScenarioDao
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [(ApplicationModule::class), (RoomModule::class)])
interface ApplicationComponent {

    fun sharedPreferencesManager(): SharedPreferencesManager
    fun scenarioDao(): ScenarioDao
    fun context(): Context
}
