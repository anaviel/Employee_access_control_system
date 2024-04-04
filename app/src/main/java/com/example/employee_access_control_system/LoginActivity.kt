package com.example.employee_access_control_system

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        val textLogin = findViewById<EditText>(R.id.editTextLogin)
        val textPassword = findViewById<EditText>(R.id.editTextPassword)
        val buttonLogin = findViewById<Button>(R.id.buttonLogin)

        buttonLogin.setOnClickListener {
            val login = textLogin.text.toString()
            val password = textPassword.text.toString()

            if (login.isNotBlank() && password.isNotBlank()) {
                auth.signInWithEmailAndPassword(login, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            val uid = user?.uid
                            uid?.let { checkUserRole(it) }
                        } else {
                            // Если произошла ошибка, отобразить сообщение об ошибке
                            Toast.makeText(
                                baseContext, "Логин или пароль введены неверено",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            } else {
                // Если поля логина или пароля пустые, выводим сообщение об ошибке
                Toast.makeText(this, "Пожалуйста, введите логин и пароль", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkUserRole(uid: String) {
        val employeeRef = database.getReference("employees").child(uid)
        val adminRef = database.getReference("admins").child(uid)

        employeeRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    // Пользователь является сотрудником
                    val intent = Intent(this@LoginActivity, AccountActivity::class.java)
                    startActivity(intent)
                } else {
                    // Пользователь не является сотрудником, проверим, является ли он администратором
                    adminRef.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.exists()) {
                                // Пользователь является администратором
                                val intent = Intent(this@LoginActivity, AdminAccountActivity::class.java)
                                startActivity(intent)
                            } else {
                                // Пользователь не является ни сотрудником, ни администратором
                                Toast.makeText(this@LoginActivity, "Данные введены неверно", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            // Обработка ошибок при чтении из базы данных
                            Toast.makeText(this@LoginActivity, "Ошибка при чтении данных из базы данных", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Обработка ошибок при чтении из базы данных
                Toast.makeText(this@LoginActivity, "Ошибка при чтении данных из базы данных", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
