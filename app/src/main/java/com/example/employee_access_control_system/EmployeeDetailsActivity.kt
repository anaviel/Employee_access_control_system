package com.example.employee_access_control_system

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class EmployeeDetailsActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.employee_details)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        // Получение данных о сотруднике из Intent
        val id = intent.getStringExtra("id")
        val timestamp = intent.getStringExtra("timestamp")
        val type = intent.getStringExtra("type")
        val date = intent.getStringExtra("date")

        // Нахождение TextView в макете
        val timestampTextView = findViewById<TextView>(R.id.timestampTextView)
        val typeTextView = findViewById<TextView>(R.id.typeTextView)
        val employeeNameTextView = findViewById<TextView>(R.id.employeeNameTextView)
        val dateTextView = findViewById<TextView>(R.id.dateTextView)

        // Установка полученных данных в TextView
        timestampTextView.text = "Время: $timestamp"
        typeTextView.text = "Тип дествия: $type"

        // Получение имени сотрудника из базы данных
        id?.let { uid ->
            val employeeNameRef = database.child("employees").child(uid).child("fullName")
            employeeNameRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val employeeName = snapshot.value.toString()
                    employeeNameTextView.text = "Имя сотрудника: $employeeName"
                }

                override fun onCancelled(error: DatabaseError) {
                    // Обработка ошибок при чтении данных
                }
            })
        }

        // Установка даты в TextView
        date?.let {
            dateTextView.text = "Дата: $it"
        }
    }
}
