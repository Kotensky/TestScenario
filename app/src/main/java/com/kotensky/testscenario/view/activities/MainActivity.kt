package com.kotensky.testscenario.view.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.kotensky.testscenario.R
import com.kotensky.testscenario.di.components.DaggerScreenComponent
import com.kotensky.testscenario.interfaces.ListItemClickListener
import com.kotensky.testscenario.interfaces.view.ScenarioView
import com.kotensky.testscenario.model.entities.ScenarioEntity
import com.kotensky.testscenario.presenter.ScenarioPresenter
import com.kotensky.testscenario.view.adapter.ScenariosAdapter
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : BaseActivity(), ScenarioView, ListItemClickListener {

    companion object {
       const val REQUEST_CODE_ADD_SCENARIO = 1
       const val SCENARIO_ID_KEY = "com.kotensky.testscenario.SCENARIO_ID_KEY"
    }

    @Inject
    lateinit var presenter: ScenarioPresenter
    @Inject
    lateinit var scenariosAdapter: ScenariosAdapter

    private val scenarios = ArrayList<ScenarioEntity?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupDefaultState()

    }

    private fun setupDefaultState() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.activity_main_title)
        presenter.view = this

        scenariosAdapter.scenarios = scenarios
        scenariosAdapter.listener = this
        scenariosRecycler.adapter = scenariosAdapter
        scenariosRecycler.layoutManager = LinearLayoutManager(this)

        addScenarioFab.setOnClickListener {
            startActivityForResult(Intent(this, AddScenarioActivity::class.java), REQUEST_CODE_ADD_SCENARIO)
        }
        presenter.getAllScenarios()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_ADD_SCENARIO) {
            presenter.getAllScenarios()
        }
    }

    override fun onItemClick(position: Int) {
        val intent = Intent(this, ScenarioFinalActivity::class.java)
        intent.putExtra(SCENARIO_ID_KEY, scenarios.getOrNull(position)?.id)
        startActivity(intent)
    }

    override fun showScenarios(scenarios: List<ScenarioEntity?>) {
        this.scenarios.clear()
        this.scenarios.addAll(scenarios)
        scenariosAdapter.notifyDataSetChanged()
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
