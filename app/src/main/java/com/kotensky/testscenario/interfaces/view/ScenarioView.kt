package com.kotensky.testscenario.interfaces.view

import com.kotensky.testscenario.model.entities.ScenarioEntity

interface ScenarioView {

    fun onScenarioAdded(){}
    fun showScenario(scenarioEntity: ScenarioEntity){}
    fun showScenarios(scenarios: List<ScenarioEntity?>){}

}