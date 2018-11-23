package com.kotensky.testscenario.model.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.kotensky.testscenario.model.entities.ScenarioEntity
import com.kotensky.testscenario.model.room.dao.ScenarioDao


@Database(entities = [(ScenarioEntity::class)], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun scenarioDao(): ScenarioDao

}