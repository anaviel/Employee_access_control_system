package com.example.employee_access_control_system

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val textLogin = findViewById<TextView>(R.id.textLogin)
        val editTextPassword = findViewById<TextView>(R.id.textPassword)
        val buttonLogin = findViewById<Button>(R.id.buttonLogin)

        buttonLogin.setOnClickListener {
            val login = textLogin.text.toString()
            val password = editTextPassword.text.toString()

            if (login == "admin" && password == "admin") {
                // Если логин и пароль верные, перейти к MainActivity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {

            }
        }
    }
}