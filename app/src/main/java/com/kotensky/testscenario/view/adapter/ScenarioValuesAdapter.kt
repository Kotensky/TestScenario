package com.kotensky.testscenario.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotensky.testscenario.R
import com.kotensky.testscenario.utils.convertValueToTime
import com.kotensky.testscenario.utils.getValueStartIndexes
import kotlinx.android.synthetic.main.scenario_value_item.view.*
import javax.inject.Inject

class ScenarioValuesAdapter @Inject constructor() :
        RecyclerView.Adapter<ScenarioValuesAdapter.ValueViewHolder>() {

    var values: Array<Int>? = null
        set(value) {
            field = value
            notifyValuesChanged()
        }

    private var valueStartIndexes = ArrayList<Int>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ValueViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.scenario_value_item, parent, false);
        return ValueViewHolder(view)
    }

    override fun getItemCount(): Int = valueStartIndexes.size

    override fun onBindViewHolder(holder: ValueViewHolder, position: Int) {
        if (itemCount == 0 || values == null)
            return

        val valueFirstIndex = valueStartIndexes.getOrNull(position) ?: return
        val valueSecondIndex = valueStartIndexes.getOrNull(position + 1) ?: values!!.size
        val value = values!!.getOrNull(valueFirstIndex) ?: return

        holder.bindView(valueFirstIndex, valueSecondIndex, value)
    }

    fun notifyValuesChanged() {
        valueStartIndexes = getValueStartIndexes(values)
        notifyDataSetChanged()
    }


    class ValueViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(valueFirstIndex: Int, valueSecondIndex: Int, value: Int) {
            itemView.timeStartTxt?.text = convertValueToTime(valueFirstIndex)
            itemView.timeEndTxt?.text = convertValueToTime(valueSecondIndex)
            itemView.temperatureTxt?.text = itemView.context.getString(R.string.temperature_template, value)
        }

    }
}