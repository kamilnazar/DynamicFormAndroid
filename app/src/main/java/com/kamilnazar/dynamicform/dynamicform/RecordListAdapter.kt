package com.kamilnazar.dynamicform.dynamicform

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kamilnazar.dynamicform.R
import com.kamilnazar.dynamicform.util.RecordList
import kotlinx.android.synthetic.main.item_person_detail.view.*

class RecordListAdapter(private val recordList: RecordList) :
    RecyclerView.Adapter<RecordViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        return RecordViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_person_detail,
                parent, false
            )
        )
    }

    override fun getItemCount(): Int = recordList.size

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        val data = recordList[position]
        val validData = data.filterValues { it != null }.asSequence()
            .map { "${it.key.capitalize()} : ${it.value.toString()}" }

        holder.itemView.dataline1.text = validData.elementAtOrNull(0)
        holder.itemView.dataline2.text = validData.elementAtOrNull(1)

    }

    fun addRecord(record: HashMap<String, Any?>) {
        this.recordList.add(record)
        notifyItemInserted(this.recordList.size - 1)
    }
}
