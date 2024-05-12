package com.example.employee_access_control_system

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HistoryAdapter(private val historyList: List<History>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    private var itemClickListener: HistoryItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val history = historyList[position]
        holder.bind(history)

        holder.employeeButton.setOnClickListener {
            itemClickListener?.onItemClick(history)
        }
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val employeeButton: Button = itemView.findViewById(R.id.employeeButton)

        fun bind(history: History) {
            employeeButton.text = history.fullName
        }
    }

    interface HistoryItemClickListener {
        fun onItemClick(history: History)
    }

    fun setOnItemClickListener(listener: HistoryItemClickListener) {
        itemClickListener = listener
    }
}


