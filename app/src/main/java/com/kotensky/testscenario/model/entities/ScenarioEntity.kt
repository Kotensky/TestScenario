package com.kotensky.testscenario.model.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "scenario")
class ScenarioEntity(
        @PrimaryKey(autoGenerate = true)
        var id: Long? = null,
        var name: String,
        var values: Array<Int>)