package com.example.employee_access_control_system

import HistoryAdapter
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class EmployeeHistoryActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var recyclerView: RecyclerView
    private lateinit var button1: Button
    private lateinit var button2: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_history)

        // Инициализируем FirebaseAuth
        auth = FirebaseAuth.getInstance()

        recyclerView = findViewById(R.id.recyclerView)
        button1 = findViewById(R.id.button1)
        button2 = findViewById(R.id.button2)

        // Получаем текущего пользователя
        val currentUser: FirebaseUser? = auth.currentUser

        // Получаем ссылку на базу данных Firebase
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val historyRef: DatabaseReference = database.getReference("history")

        // Добавляем слушатель для чтения данных из базы данных
        historyRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    // Проходим по всем дочерним узлам
                    val historyList = mutableListOf<History>()
                    for (childSnapshot in snapshot.children) {
                        // Преобразуем каждый дочерний снимок в объект класса History
                        val history = childSnapshot.getValue(History::class.java)
                        history?.let {
                            historyList.add(it)
                        }
                    }
                    // Создаем адаптер для RecyclerView
                    val adapter = HistoryAdapter(historyList)
                    // Устанавливаем адаптер для RecyclerView
                    recyclerView.adapter = adapter
                    // Устанавливаем LayoutManager для RecyclerView
                    recyclerView.layoutManager = LinearLayoutManager(this@EmployeeHistoryActivity)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Обработка ошибки чтения данных из базы данных
            }
        })

        // Настройка обработчика нажатия для кнопки "Назад"
        button1.setOnClickListener {
            // Ваш код обработки нажатия кнопки "Назад" здесь
        }

        // Настройка обработчика нажатия для кнопки "Вперед"
        button2.setOnClickListener {
            // Ваш код обработки нажатия кнопки "Вперед" здесь
        }
    }
}
