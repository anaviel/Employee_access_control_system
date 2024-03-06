package com.example.employee_access_control_system

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textEnterSystem = findViewById<TextView>(R.id.textEnterSystem)
        val plainTextLogin = findViewById<EditText>(R.id.textLogin)
        val plainTextPassword = findViewById<EditText>(R.id.textPassword)
        val buttonLogin = findViewById<Button>(R.id.buttonEnterSystem)

        button.setOnClickListener {
            var userLogin = plainTextLogin.text.toString().trim()
            var userPassword = plainTextPassword.text.toString().trim()
        }
    }
}