package com.example.employee_access_control_system

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class AccountActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        auth = FirebaseAuth.getInstance()

        val textViewFullName = findViewById<TextView>(R.id.textViewFullName)
        val textViewPosition = findViewById<TextView>(R.id.textViewPosition)
        val textViewPhoneNumber = findViewById<TextView>(R.id.textViewPhoneNumber)
        val buttonShowVisitHistory = findViewById<Button>(R.id.buttonShowVisitHistory)
        val buttonShowQRCode = findViewById<Button>(R.id.buttonShowQRCode)

        val currentUser = auth.currentUser

        // Если текущий пользователь не равен null, получаем его UID
        currentUser?.uid?.let { uid ->
            // Получаем ссылку на базу данных Firebase и на узел с UID текущего пользователя
            val database = FirebaseDatabase.getInstance()
            val employeeRef = database.getReference("employees").child(uid)

            // Слушатель для получения данных из базы данных один раз
            employeeRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val employee = snapshot.getValue(Employee::class.java)
                        if (employee != null) {
                            if (employee.fullName.isNotBlank() && employee.position.isNotBlank() && employee.phoneNumber.isNotBlank()) {
                                textViewFullName.text = employee.fullName
                                textViewPosition.text = employee.position
                                textViewPhoneNumber.text = employee.phoneNumber
                            } else {
                                // Если какие-то из полей пустые, отобразить сообщение об ошибке
                                Toast.makeText(this@AccountActivity, "Некоторые поля сотрудника пустые", Toast.LENGTH_SHORT).show()
                                Log.e("AccountActivity", "Некоторые поля сотрудника пустые")
                            }
                        } else {
                            // Если объект employee равен null, отобразить сообщение об ошибке
                            Toast.makeText(this@AccountActivity, "Данные сотрудника не найдены", Toast.LENGTH_SHORT).show()
                            Log.e("AccountActivity", "Данные сотрудника не найдены")
                        }
                    } else {
                        // Если данных не существует, отобразить сообщение об ошибке
                        Toast.makeText(this@AccountActivity, "Данные сотрудника не найдены", Toast.LENGTH_SHORT).show()
                        Log.e("AccountActivity", "Снимок данных не существует")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Обработка ошибок при чтении из базы данных
                    // Может быть добавлена логика обработки ошибок
                    Log.e("AccountActivity", "Ошибка при чтении данных из базы данных: ${error.message}")
                }
            })
        }

        // Обработчик нажатия на кнопку "Открыть историю посещений"
        buttonShowVisitHistory.setOnClickListener {
            //val intent = Intent(this, EmployeeHistoryActivity::class.java)
            //startActivity(intent)
        }

        buttonShowQRCode.setOnClickListener {
            // Обработчик нажатия на кнопку "Открыть историю посещений"
        }
    }
}


