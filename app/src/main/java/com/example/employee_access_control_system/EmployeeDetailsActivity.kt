package com.example.employee_access_control_system


import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class EmployeeDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.employee_details)

        // Получение данных о сотруднике из предыдущей активности
        val id = intent.getStringExtra("id")
        val timestamp = intent.getStringExtra("timestamp")
        val type = intent.getStringExtra("type")
        val employeeName = intent.getStringExtra("employeeName")

        // Нахождение TextView в макете
        val idTextView = findViewById<TextView>(R.id.idTextView)
        val timestampTextView = findViewById<TextView>(R.id.timestampTextView)
        val typeTextView = findViewById<TextView>(R.id.typeTextView)
        val employeeNameTextView = findViewById<TextView>(R.id.employeeNameTextView)

        // Установка полученных данных в TextView
        idTextView.text = "ID: $id"
        timestampTextView.text = "Timestamp: $timestamp"
        typeTextView.text = "Type: $type"
        employeeNameTextView.text = "Employee Name: $employeeName"
    }
}