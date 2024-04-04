package com.example.employee_access_control_system

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class EmployeeHistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_history)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        val historyList = listOf(
            "Запись 1: 10:00 - Вход",
            "Запись 2: 12:00 - Выход",
            "Запись 3: 14:00 - Вход",
            "Запись 4: 16:00 - Выход"
        )

        // Создаем адаптер для RecyclerView
        val adapter = HistoryAdapter(historyList)

        // Устанавливаем адаптер для RecyclerView
        recyclerView.adapter = adapter

        // Устанавливаем LayoutManager для RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}