package com.kotensky.testscenario.model.room

import android.arch.persistence.room.TypeConverter

class Converters {

    @TypeConverter
    fun toArray(valuesInStr: String): Array<Int> {
        val values = Array(24, { 0 })
        valuesInStr.split(",")
                .forEachIndexed { index, s ->
                    if (index < values.size && s.toIntOrNull() != null)
                        values[index] = s.toInt()
                }

        return values
    }

    @TypeConverter
    fun fromArray(values: Array<Int>): String {
        var string = ""
        for (s in values)
            string += "$s,"
        return string
    }

}