package com.example.employee_access_control_system

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class EmployeeHistoryActivity : AppCompatActivity(), HistoryAdapter.HistoryItemClickListener {

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
        val employeesRef: DatabaseReference = database.getReference("employees")
        val employee1TextView = findViewById<TextView>(R.id.employee1)
        employee1TextView.setOnClickListener {
            val employeeName = employee1TextView.text.toString() // Получаем имя сотрудника из TextView
            val intent = Intent(this, EmployeeDetailsActivity::class.java).apply {
                putExtra("employeeName", employeeName)
            }
            startActivity(intent)
        }

        employeesRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    historyList.clear()
                    for (employeeSnapshot in snapshot.children) {
                        val employee = employeeSnapshot.getValue(Employee::class.java)
                        employee?.let {
                            // Добавляем информацию о сотруднике в список для отображения
                            val employeeName = employee.fullName // Здесь предполагается, что у сотрудника есть поле fullName
                            // Создаем элемент истории для каждого сотрудника
                            val historyItem = History(employeeName, "", "") // Второй и третий параметры можно оставить пустыми, так как они относятся к времени и типу действия
                            historyList.add(historyItem)
                        }
                    }
                    val adapter = HistoryAdapter(historyList)
                    recyclerView.adapter = adapter
                    recyclerView.layoutManager = LinearLayoutManager(this@EmployeeHistoryActivity)

                    adapter.setOnItemClickListener(this@EmployeeHistoryActivity)
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

    override fun onItemClick(history: History) {
        // Обработка нажатия на элемент списка истории
    }
}