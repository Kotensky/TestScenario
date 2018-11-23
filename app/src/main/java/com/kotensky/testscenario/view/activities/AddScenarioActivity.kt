package com.kotensky.testscenario.view.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.kotensky.testscenario.R
import com.kotensky.testscenario.di.components.DaggerScreenComponent
import com.kotensky.testscenario.interfaces.OnValuePickListener
import com.kotensky.testscenario.interfaces.view.ScenarioView
import com.kotensky.testscenario.managers.SharedPreferencesManager
import com.kotensky.testscenario.model.entities.ScenarioEntity
import com.kotensky.testscenario.presenter.ScenarioPresenter
import com.kotensky.testscenario.utils.createPickValueDialog
import com.kotensky.testscenario.view.adapter.ScenarioValuesAdapter
import kotlinx.android.synthetic.main.activity_add_scenario.*
import javax.inject.Inject


class AddScenarioActivity : BaseActivity(), OnValuePickListener, ScenarioView {

    @Inject
    lateinit var sharedPreferencesManager: SharedPreferencesManager
    @Inject
    lateinit var presenter: ScenarioPresenter
    @Inject
    lateinit var valuesAdapter: ScenarioValuesAdapter

    private var pickValueDialog: AlertDialog? = null

    private lateinit var values: Array<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_scenario)
        setupDefaultState()
    }

    private fun setupDefaultState() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        presenter.view = this

        val defaultTempValue = sharedPreferencesManager.getDefaultTemp()
        values = Array(24, { defaultTempValue })

        pickValueDialog = createPickValueDialog(this, defaultTempValue, this)
        pickValueBtn.setOnClickListener {
            pickValueDialog?.show()
        }

        setupValuesRecycler()

    }

    private fun setupValuesRecycler() {

        valuesAdapter.values = values
        scenarioValuesRecycler.adapter = valuesAdapter
        scenarioValuesRecycler.layoutManager = LinearLayoutManager(this)
    }

    private fun onAcceptClick() {
        if (scenarioTitleEdt?.text?.toString().isNullOrEmpty()) {
            showToast(getString(R.string.name_empty_error))
        } else {
            presenter.addScenario(
                    ScenarioEntity(
                            name = scenarioTitleEdt.text.toString(),
                            values = values))
        }
    }

    override fun onValuePick(from: Int, to: Int, value: Int) {
        for (i in from..to) {
            if (i < values.size)
                values[i] = value
        }
        valuesAdapter.notifyValuesChanged()
    }

    override fun onScenarioAdded() {
        setResult(RESULT_OK, Intent())
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add_scenatio, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.actionAccept -> {
                onAcceptClick()
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
        pickValueDialog = null
        super.onDestroy()
    }
}
