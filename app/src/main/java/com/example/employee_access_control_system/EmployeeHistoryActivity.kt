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
    private lateinit var searchButton: ImageButton
    private val historyMap = mutableMapOf<String, History>()

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
                    historyMap.clear()
                    for (historySnapshot in snapshot.children) {
                        val id = historySnapshot.key ?: ""
                        val uid = historySnapshot.child("uid").getValue(String::class.java) ?: ""
                        val date = historySnapshot.child("date").getValue(String::class.java) ?: ""
                        val fullName =
                            historySnapshot.child("fullName").getValue(String::class.java) ?: ""
                        val time = historySnapshot.child("time").getValue(String::class.java) ?: ""
                        val type = historySnapshot.child("type").getValue(String::class.java) ?: ""

                        // Проверяем, существует ли уже запись с таким uid в historyMap
                        if (!historyMap.containsKey(uid)) {
                            val history = History(id, uid, date, fullName, time, type)
                            historyMap[uid] = history // Добавляем запись в historyMap
                        }
                    }
                    // Обновляем RecyclerView после обработки данных
                    val adapter = HistoryAdapter(historyMap.values.toList())
                    recyclerView.adapter = adapter
                    recyclerView.layoutManager = LinearLayoutManager(this@EmployeeHistoryActivity)
                    adapter.setOnItemClickListener(object :
                        HistoryAdapter.HistoryItemClickListener {
                        override fun onItemClick(history: History) {
                            val intent = Intent(
                                this@EmployeeHistoryActivity,
                                EmployeeDetailsActivity::class.java
                            ).apply {
                                putExtra("id", history.uid)
                                putExtra("time", history.time)
                                putExtra("type", history.type)
                                putExtra("fullName", history.fullName)
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
        val filteredList = historyMap.values.filter { it.fullName.contains(query, ignoreCase = true) }
        if (filteredList.isNotEmpty()) {
            val adapter = HistoryAdapter(filteredList)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this@EmployeeHistoryActivity)

            adapter.setOnItemClickListener(object : HistoryAdapter.HistoryItemClickListener {
                override fun onItemClick(history: History) {
                    val intent = Intent(this@EmployeeHistoryActivity, EmployeeDetailsActivity::class.java).apply {
                        putExtra("id", history.id)
                        putExtra("time", history.time)
                        putExtra("type", history.type)
                        putExtra("fullName", history.fullName)
                        putExtra("date", history.date)
                    }
                    startActivity(intent)
                }
            })
        } else {
            Toast.makeText(this, "Сотрудник не найден", Toast.LENGTH_SHORT).show()
        }
    }
}
