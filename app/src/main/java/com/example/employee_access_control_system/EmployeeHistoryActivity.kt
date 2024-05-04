package com.example.employee_access_control_system

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_history)

        auth = FirebaseAuth.getInstance()
        recyclerView = findViewById(R.id.recyclerView)
        button2 = findViewById(R.id.backButton)

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
                            // Добавляем информацию о сотруднике в список для отображения
                            val date = employee.date
                            val employeeName = employee.employeeName
                            val timestamp = employee.timestamp
                            val type = employee.type
                            Log.d("EmployeeHistoryActivity", "UID: $uid, Date: $date, EmployeeName: $employeeName, Timestamp: $timestamp, Type: $type")
                            // Создаем элемент истории для каждого сотрудника с UID
                            val historyItem = History(uid, date, employeeName, timestamp, type)
                            historyList.add(historyItem)
                        }
                    }
                    val adapter = HistoryAdapter(historyList)
                    recyclerView.adapter = adapter
                    recyclerView.layoutManager = LinearLayoutManager(this@EmployeeHistoryActivity)
                    adapter.setOnItemClickListener(object : HistoryAdapter.HistoryItemClickListener {
                        override fun onItemClick(history: History) {
                            val intent = Intent(this@EmployeeHistoryActivity, EmployeeDetailsActivity::class.java)
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


        button2.setOnClickListener {
            // Обработчик для кнопки 2
        }
    }
}
