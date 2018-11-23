package com.kotensky.testscenario.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotensky.testscenario.R
import com.kotensky.testscenario.interfaces.ListItemClickListener
import com.kotensky.testscenario.model.entities.ScenarioEntity
import kotlinx.android.synthetic.main.scenario_item.view.*
import javax.inject.Inject

class ScenariosAdapter @Inject constructor() :
        RecyclerView.Adapter<ScenariosAdapter.ScenariosViewHolder>() {

    var scenarios: ArrayList<ScenarioEntity?>? = null
    var listener: ListItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScenariosViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.scenario_item, parent, false);
        return ScenariosViewHolder(view)
    }

    override fun getItemCount() = scenarios?.size ?: 0

    override fun onBindViewHolder(holder: ScenariosViewHolder, position: Int) {
        holder.itemView.nameTxt?.text = scenarios?.getOrNull(position)?.name
        holder.itemView.nameContainer?.setOnClickListener {
            listener?.onItemClick(position)
        }
    }


    class ScenariosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}