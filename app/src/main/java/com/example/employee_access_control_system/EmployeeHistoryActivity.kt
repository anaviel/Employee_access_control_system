package com.example.employee_access_control_system

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class EmployeeHistoryActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var recyclerView: RecyclerView
    private lateinit var button2: ImageButton
    private val historyList = mutableListOf<History>()
    private lateinit var searchButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_history)

        auth = FirebaseAuth.getInstance()
        recyclerView = findViewById(R.id.recyclerView)
        button2 = findViewById(R.id.backButton)
        searchButton = findViewById(R.id.searchButton)

        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val historyRef: DatabaseReference = database.getReference("History")

        historyRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    historyList.clear()
                    for (historySnapshot in snapshot.children) {
                        val uid = historySnapshot.key ?: ""
                        val employee = historySnapshot.getValue(History::class.java)
                        employee?.let {
                            // Проверяем, существует ли уже карточка истории для данного сотрудника
                            val existingHistory = historyList.find { it.uid == uid }
                            if (existingHistory != null) {
                                // Если карточка уже существует, обновляем ее данные
                                existingHistory.date = employee.date
                                existingHistory.employeeName = employee.employeeName
                                existingHistory.timestamp = employee.timestamp
                                existingHistory.type = employee.type
                            } else {
                                // Если карточка не существует, создаем новую
                                val historyItem = History(uid, employee.date, employee.employeeName, employee.timestamp, employee.type)
                                historyList.add(historyItem)
                            }
                        }
                    }
                    // Обновляем RecyclerView после обработки данных
                    val adapter = HistoryAdapter(historyList)
                    recyclerView.adapter = adapter
                    recyclerView.layoutManager = LinearLayoutManager(this@EmployeeHistoryActivity)
                    adapter.setOnItemClickListener(object : HistoryAdapter.HistoryItemClickListener {
                        override fun onItemClick(history: History) {
                            val intent = Intent(this@EmployeeHistoryActivity, EmployeeDetailsActivity::class.java).apply {
                                putExtra("id", history.uid)
                                putExtra("timestamp", history.timestamp)
                                putExtra("type", history.type)
                                putExtra("employeeName", history.employeeName)
                                putExtra("date", history.date)
                            }
                            startActivity(intent)
                        }
                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Обработка ошибок при чтении из базы данных
                Log.e("EmployeeHistoryActivity", "Ошибка при чтении данных из базы данных: ${error.message}")
            }
        })


        // Обработка нажатия на кнопку "Назад"
        button2.setOnClickListener {
            finish()
        }

        // Обработка нажатия на кнопку "Поиск"
        searchButton.setOnClickListener {
            showSearchDialog()
        }
    }

    // Метод для отображения диалогового окна поиска
    private fun showSearchDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Поиск сотрудника")
        val input = EditText(this)
        builder.setView(input)
        builder.setPositiveButton("Поиск") { dialog, _ ->
            val searchQuery = input.text.toString().trim()
            if (searchQuery.isNotEmpty()) {
                searchEmployees(searchQuery)
            } else {
                Toast.makeText(this, "Введите имя сотрудника для поиска", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }
        builder.setNegativeButton("Отмена") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    // Метод для выполнения поиска сотрудников
    private fun searchEmployees(query: String) {
        val filteredList = historyList.filter { it.employeeName.contains(query, ignoreCase = true) }
        if (filteredList.isNotEmpty()) {
            val adapter = HistoryAdapter(filteredList.toMutableList())
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this@EmployeeHistoryActivity)

            // Устанавливаем обработчик нажатия после обновления RecyclerView
            adapter.setOnItemClickListener(object : HistoryAdapter.HistoryItemClickListener {
                override fun onItemClick(history: History) {
                    val intent = Intent(this@EmployeeHistoryActivity, EmployeeDetailsActivity::class.java).apply {
                        putExtra("id", history.uid)
                        putExtra("timestamp", history.timestamp)
                        putExtra("type", history.type)
                        putExtra("employeeName", history.employeeName)
                        putExtra("date", history.date)
                    }
                    startActivity(intent)
                }
            })
        } else {
            Toast.makeText(this, "Сотрудник не найден", Toast.LENGTH_SHORT).show()
        }
    }}
