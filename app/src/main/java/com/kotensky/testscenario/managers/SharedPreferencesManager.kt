package com.kotensky.testscenario.managers

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton



@Singleton
class SharedPreferencesManager @Inject constructor(var context: Context) {

    private val PREFERENCES_FILE_NAME = "com.kotensky.testscenario.sp"

    private val DEFAULT_TEMP = "DEFAULT_TEMP"


    private var sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
    }


    fun setDefaultTemp(defaultTemp: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(DEFAULT_TEMP, defaultTemp)
        editor.apply()
    }

    fun getDefaultTemp(): Int {
        return sharedPreferences.getInt(DEFAULT_TEMP, 13)
    }


}