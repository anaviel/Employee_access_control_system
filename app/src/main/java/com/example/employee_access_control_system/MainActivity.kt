package com.example.employee_access_control_system

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // При запуске приложения сначала открываем экран входа
        startActivity(Intent(this, LoginActivity::class.java))
        // Закрываем текущую активность, чтобы пользователь не мог вернуться к MainActivity
        finish()
    }
}