package com.kotensky.testscenario.di.modules

import android.arch.persistence.room.Room
import android.content.Context
import com.kotensky.testscenario.model.room.AppDatabase
import com.kotensky.testscenario.model.room.dao.ScenarioDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RoomModule {

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "scenarios-app-db").build()
    }

    @Provides
    @Singleton
    fun provideScenarioDao(appDatabase: AppDatabase): ScenarioDao {
        return appDatabase.scenarioDao()
    }

}