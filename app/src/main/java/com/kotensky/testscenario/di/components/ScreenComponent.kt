package com.kotensky.testscenario.di.components

import com.kotensky.testscenario.di.ScreenScope
import com.kotensky.testscenario.view.activities.AddScenarioActivity
import com.kotensky.testscenario.view.activities.MainActivity
import com.kotensky.testscenario.view.activities.ScenarioFinalActivity
import dagger.Component

@ScreenScope
@Component(dependencies = [(ApplicationComponent::class)])
interface ScreenComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(addScenarioActivity: AddScenarioActivity)
    fun inject(scenarioFinalActivity: ScenarioFinalActivity)

}