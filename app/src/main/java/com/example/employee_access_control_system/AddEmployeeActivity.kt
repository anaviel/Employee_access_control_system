package com.example.employee_access_control_system

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.*

class AddEmployeeActivity : AppCompatActivity() {

    private lateinit var fullNameEditText: EditText
    private lateinit var phoneNumberEditText: EditText
    private lateinit var positionEditText: EditText
    private lateinit var addEmployeeButton: Button
    private lateinit var loginPasswordTextView: TextView
    private lateinit var button2: ImageButton

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var employeesRef: DatabaseReference

    // Определите переменную для хранения сгенерированного пароля
    private var generatedPassword: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_employee)

        fullNameEditText = findViewById(R.id.editTextAddFullName)
        phoneNumberEditText = findViewById(R.id.editTextAddPhoneNumber)
        positionEditText = findViewById(R.id.editTextAddPosition)
        addEmployeeButton = findViewById(R.id.buttonAddEmployee)
        loginPasswordTextView = findViewById(R.id.textViewLoginPassword)
        button2 = findViewById(R.id.backButton)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        employeesRef = database.getReference("employees")

        addEmployeeButton.setOnClickListener {
            generateAndSavePassword() // Генерация и сохранение пароля перед добавлением сотрудника
            addEmployee()
        }

        // Обработка нажатия на кнопку "Назад"
        button2.setOnClickListener {
            finish()
        }
    }

    private fun addEmployee() {
        val fullName = fullNameEditText.text.toString()
        val phoneNumber = phoneNumberEditText.text.toString()
        val position = positionEditText.text.toString()


        // Получаем текущее количество сотрудников из базы данных
        employeesRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val employeeCount = snapshot.childrenCount.toInt() + 1 // Увеличиваем количество на 1 для создания следующего логина
                val login = "employee" + String.format("%06d", employeeCount) // Форматируем номер для получения логина вида "employee000001"

                // Регистрация нового пользователя в Firebase Authentication
                auth.createUserWithEmailAndPassword("$login@gmail.com", generatedPassword)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Если регистрация успешна, добавляем данные сотрудника в базу данных Realtime Database
                            val user = auth.currentUser
                            val uid = user?.uid ?: ""
                            val employeeRef = employeesRef.child(uid)
                            employeeRef.child("fullName").setValue(fullName)
                            employeeRef.child("phoneNumber").setValue(phoneNumber)
                            employeeRef.child("position").setValue(position)
                            employeeRef.child("status").setValue(0)

                            // Отображение логина и пароля нового сотрудника
                            loginPasswordTextView.text = "Был добавлен новый сотрудник.\n\nЛогин: $login@gmail.com\nПароль: $generatedPassword"
                        } else {
                            // Если регистрация не удалась, выведите сообщение об ошибке
                            Toast.makeText(this@AddEmployeeActivity, "Ошибка при регистрации пользователя: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }

            override fun onCancelled(error: DatabaseError) {
                // Обработка ошибок при чтении из базы данных
                Log.e("AddEmployeeActivity", "Ошибка при чтении количества сотрудников из базы данных: ${error.message}")
            }
        })
    }

    // Вызов этого метода для генерации пароля и сохранения его в переменной
    private fun generateAndSavePassword() {
        generatedPassword = generatePassword()
    }

    // Генерация случайного пароля
    private fun generatePassword(): String {
        val charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        return (1..8)
            .map { charset.random() }
            .joinToString("")
    }


}


