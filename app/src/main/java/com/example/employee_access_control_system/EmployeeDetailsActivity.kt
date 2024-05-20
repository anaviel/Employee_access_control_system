package com.example.employee_access_control_system

import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class EmployeeDetailsActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EmployeeDetailsAdapter
    private lateinit var button2: ImageButton
    private val historyList = mutableListOf<History>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_details)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        button2 = findViewById(R.id.backButton)

        // Получение UID пользователя из Intent
        val uid = intent.getStringExtra("id") ?: ""

        // Получение данных о сотруднике из Intent
        val fullName = intent.getStringExtra("fullName")
        Log.d("EmployeeDetailsActivity", "Full Name: $fullName")

        // Нахождение TextView в макете и установка имени пользователя
        val employeeNameTextView = findViewById<TextView>(R.id.employeeNameTextView)
        employeeNameTextView.text = fullName

        // Нахождение RecyclerView в макете
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = EmployeeDetailsAdapter(historyList) // Исправлено имя адаптера
        recyclerView.adapter = adapter

        // Запрос к базе данных Firebase для получения записей History с указанным UID
        val query = database.child("History").orderByChild("uid").equalTo(uid)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                historyList.clear()
                for (snapshot in dataSnapshot.children) {
                    val id = snapshot.key ?: ""
                    val time = snapshot.child("time").getValue(String::class.java) ?: ""
                    val type = snapshot.child("type").getValue(String::class.java) ?: ""
                    val date = snapshot.child("date").getValue(String::class.java) ?: ""
                    val fullName = snapshot.child("fullName").getValue(String::class.java) ?: ""
                    val history = History(id, uid, date, fullName, time, type)
                    historyList.add(history)
                }

                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("EmployeeDetailsActivity", "Ошибка при чтении данных из базы данных")
            }
        })

        // Обработка нажатия на кнопку "Назад"
        button2.setOnClickListener {
            finish()
        }
    }
}


