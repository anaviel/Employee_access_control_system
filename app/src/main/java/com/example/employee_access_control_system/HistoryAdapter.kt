package com.example.employee_access_control_system

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        holder.itemView.setOnClickListener {
            itemClickListener?.onItemClick(history)
        }
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val idTextView: TextView = itemView.findViewById(R.id.idTextView)
        private val timestampTextView: TextView = itemView.findViewById(R.id.timestampTextView)
        private val typeTextView: TextView = itemView.findViewById(R.id.typeTextView)

        fun bind(history: History) {
            idTextView.text = "ID: ${history.id}"
            timestampTextView.text = "Timestamp: ${history.timestamp}"
            typeTextView.text = "Type: ${history.type}"
        }
    }

    interface HistoryItemClickListener {
        fun onItemClick(history: History)
    }

    fun setOnItemClickListener(listener: HistoryItemClickListener) {
        itemClickListener = listener
    }
}
