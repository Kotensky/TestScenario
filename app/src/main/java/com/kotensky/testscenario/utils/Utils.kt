package com.kotensky.testscenario.utils

import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.widget.NumberPicker
import com.kotensky.testscenario.R
import com.kotensky.testscenario.interfaces.OnValuePickListener
import java.lang.reflect.InvocationTargetException
import java.util.*


fun getValueStartIndexes(values: Array<Int>?): ArrayList<Int> {
    val valueStartIndexes = ArrayList<Int>()
    values?.forEachIndexed { index, value ->
        if (index == 0 || value != values[index - 1]) {
            valueStartIndexes.add(index)
        }
    }
    return valueStartIndexes
}

fun convertValueToTime(value: Int?): String {
    return when {
        value == null -> ""
        value < 10 -> "0$value:00"
        value == 24 -> "00:00"
        else -> "$value:00"
    }
}

fun createPickValueDialog(context: Context, defaultTempValue: Int, listener: OnValuePickListener): AlertDialog? {
    val pickValueDialogView = LayoutInflater.from(context)?.inflate(R.layout.dialog_scenario_value_picker, null)
    val fromPicker = pickValueDialogView?.findViewById<NumberPicker>(R.id.fromPicker)
    val toPicker = pickValueDialogView?.findViewById<NumberPicker>(R.id.toPicker)
    val temperaturePicker = pickValueDialogView?.findViewById<NumberPicker>(R.id.temperaturePicker)

    val temperatureTemplateStr = context.getString(R.string.temperature_template)
    val timeFormatter = NumberPicker.Formatter { i -> convertValueToTime(i) }
    val temperatureFormatter = NumberPicker.Formatter { i -> String.format(temperatureTemplateStr, (i - 30)) }

    fromPicker?.setFormatter(timeFormatter)
    toPicker?.setFormatter(timeFormatter)
    temperaturePicker?.setFormatter(temperatureFormatter)

//      minValue must be >= 0
    fromPicker?.minValue = 0
    toPicker?.minValue = 1
    temperaturePicker?.minValue = 0

    fromPicker?.maxValue = 23
    toPicker?.maxValue = 24
    temperaturePicker?.maxValue = 70

    fromPicker?.value = 0
    toPicker?.value = 1
    temperaturePicker?.value = 30 + defaultTempValue

    setupFormattedFirstState(fromPicker)
    setupFormattedFirstState(toPicker)
    setupFormattedFirstState(temperaturePicker)


    fromPicker?.setOnValueChangedListener { numberPicker, oldVal, newVal ->
        toPicker?.minValue = newVal + 1
    }

    val builder = AlertDialog.Builder(context)
    builder.setPositiveButton(R.string.set_value, { dialogInterface, i ->


        listener.onValuePick(fromPicker?.value ?: 0,
                (toPicker?.value ?: 1) - 1,
                (temperaturePicker?.value ?: 30) - 30)

        fromPicker?.value = 0
        toPicker?.value = 1
        temperaturePicker?.value = 30 + defaultTempValue
        toPicker?.minValue = 1

    })
    builder.setOnCancelListener {
        fromPicker?.value = 0
        toPicker?.value = 1
        temperaturePicker?.value = 30 + defaultTempValue
        toPicker?.minValue = 1
    }
    builder.setView(pickValueDialogView)
    return builder.create()
}

private fun setupFormattedFirstState(picker: NumberPicker?) {
    //I honestly didn't want to do it, but it's only way for NumberPicker
    try {
        val method = picker?.javaClass?.getDeclaredMethod("changeValueByOne", Boolean::class.javaPrimitiveType)
        method?.isAccessible = true
        method?.invoke(picker, true)

    } catch (e: NoSuchMethodException) {
        e.printStackTrace()
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
    } catch (e: IllegalAccessException) {
        e.printStackTrace()
    } catch (e: InvocationTargetException) {
        e.printStackTrace()
    }
}
