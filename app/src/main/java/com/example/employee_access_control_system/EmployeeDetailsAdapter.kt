package com.example.employee_access_control_system

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EmployeeDetailsAdapter(private val histories: List<History>) : RecyclerView.Adapter<EmployeeDetailsAdapter.EmployeeDetailsViewHolder>() {

    inner class EmployeeDetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val timeTextView: TextView = itemView.findViewById(R.id.timestampTextView)
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        val typeTextView: TextView = itemView.findViewById(R.id.typeTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeDetailsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_employee_details, parent, false)
        return EmployeeDetailsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EmployeeDetailsViewHolder, position: Int) {
        val currentItem = histories[position]

        holder.timeTextView.text = "Время: ${currentItem.time}"
        holder.dateTextView.text = "Дата: ${currentItem.date}"
        holder.typeTextView.text = "Тип: ${currentItem.type}"
    }

    override fun getItemCount() = histories.size
}
