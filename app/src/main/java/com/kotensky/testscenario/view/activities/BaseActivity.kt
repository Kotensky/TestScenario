package com.kotensky.testscenario.view.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.kotensky.testscenario.application.TestScenarioApplication
import com.kotensky.testscenario.di.components.ApplicationComponent

abstract class BaseActivity:  AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
    }

    fun getApplicationComponent(): ApplicationComponent? =
            (application as TestScenarioApplication).applicationComponent

    fun showToast(text: String?){
        if (text.isNullOrEmpty())
            return
        //todo
        Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
    }

    protected abstract fun inject()

}