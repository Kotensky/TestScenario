package com.kotensky.testscenario.model.room.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.kotensky.testscenario.model.entities.ScenarioEntity
import io.reactivex.Single

@Dao
interface ScenarioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertScenario(scenarioEntity: ScenarioEntity)

    @Query("SELECT * FROM scenario WHERE id = :id LIMIT 1")
    fun getScenario(id: Long): Single<ScenarioEntity?>

    @Query("SELECT * FROM scenario")
    fun getAllScenarios(): Single<List<ScenarioEntity?>>
}