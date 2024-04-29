package com.example.employee_access_control_system

//import EmployeeHistoryActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class AdminAccountActivity:  AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adm_account)

        auth = FirebaseAuth.getInstance()
        val textViewAdminFullName = findViewById<TextView>(R.id.textViewAdminFullName)
        val textViewAdminPosition = findViewById<TextView>(R.id.textViewAdminPosition)
        val textViewAdminPhoneNumber = findViewById<TextView>(R.id.textViewAdminPhoneNumber)
        val buttonScanQRCode = findViewById<Button>(R.id.buttonScanQRCode)
        val buttonAdminVisitHistory = findViewById<Button>(R.id.buttonAdminVisitHistory)
        val buttonAddNewEmployee = findViewById<Button>(R.id.buttonAddNewEmployee)

        val currentUser = auth.currentUser

        currentUser?.let { user ->
            // Если текущий пользователь не равен null, получаем его UID
            val uid = user.uid
            // Получаем ссылку на базу данных Firebase и на узел с UID текущего пользователя
            val database = FirebaseDatabase.getInstance()
            val adminRef = database.getReference("admins").child(uid)

            // Слушатель для получения данных из базы данных один раз
            adminRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val admin = snapshot.getValue(Admin::class.java)
                        admin?.let { adminData ->
                            textViewAdminFullName.text = adminData.fullName
                            textViewAdminPosition.text = adminData.position
                            textViewAdminPhoneNumber.text = adminData.phoneNumber
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Обработка ошибок при чтении из базы данных
                    Log.e("AdminAccountActivity", "Ошибка при чтении данных из базы данных: ${error.message}")
                }
            })
        }

        buttonScanQRCode.setOnClickListener {
            // Обработчик для кнопки "Сканировать QR-код"
        }

        buttonAdminVisitHistory.setOnClickListener {
            val intent = Intent(this, EmployeeHistoryActivity::class.java)
            startActivity(intent)
        }

        buttonAddNewEmployee.setOnClickListener {
            val intent = Intent(this, AddEmployeeActivity::class.java)
            startActivity(intent)
        }
    }
}