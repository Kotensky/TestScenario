package com.kotensky.testscenario.view.activities

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import com.kotensky.testscenario.R
import com.kotensky.testscenario.di.components.DaggerScreenComponent
import com.kotensky.testscenario.interfaces.view.ScenarioView
import com.kotensky.testscenario.model.entities.ScenarioEntity
import com.kotensky.testscenario.presenter.ScenarioPresenter
import com.kotensky.testscenario.view.activities.MainActivity.Companion.SCENARIO_ID_KEY
import com.kotensky.testscenario.view.adapter.ScenarioValuesAdapter
import kotlinx.android.synthetic.main.activity_scenario_final.*
import javax.inject.Inject


class ScenarioFinalActivity : BaseActivity(), ScenarioView {

    @Inject
    lateinit var presenter: ScenarioPresenter
    @Inject
    lateinit var valuesAdapter: ScenarioValuesAdapter

    private var id: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        id = intent.getLongExtra(SCENARIO_ID_KEY, -1)
        if (id == -1L)
            finish()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scenario_final)
        setupDefaultState()
        presenter.getScenario(id)
    }

    private fun setupDefaultState() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        presenter.view = this
        setupValuesRecycler()
    }

    private fun setupValuesRecycler() {
        scenarioValuesRecycler.adapter = valuesAdapter
        scenarioValuesRecycler.layoutManager = LinearLayoutManager(this)
    }

    override fun showScenario(scenarioEntity: ScenarioEntity) {
        supportActionBar?.title = scenarioEntity.name
        valuesAdapter.values = scenarioEntity.values
        valuesAdapter.notifyValuesChanged()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun inject() {
        DaggerScreenComponent.builder()
                .applicationComponent(getApplicationComponent())
                .build()
                .inject(this)
    }

    override fun onDestroy() {
        presenter.destroy()
        super.onDestroy()
    }
}
