package com.example.employee_access_control_system

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class ScanResultHandler : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_result_handler)

        database = FirebaseDatabase.getInstance().reference
        val scannedUid = intent.getStringExtra("scannedUid")

        if (scannedUid != null) {
            searchEmployee(scannedUid)
        } else {
            showError("Произошла ошибка! Некорректный QR-код.")
        }
    }

    private fun searchEmployee(uid: String) {
        database.child("employees").child(uid).get().addOnSuccessListener { dataSnapshot ->
            if (dataSnapshot.exists()) {
                val fullName = dataSnapshot.child("fullName").getValue(String::class.java)
                if (fullName != null) {
                    saveScanResult(fullName, uid)
                } else {
                    showError("Произошла ошибка! Некорректные данные о сотруднике.")
                }
            } else {
                searchAdmin(uid)
            }
        }.addOnFailureListener {
            showError("Произошла ошибка при поиске сотрудника.")
        }
    }

    private fun searchAdmin(uid: String) {
        database.child("admins").child(uid).get().addOnSuccessListener { dataSnapshot ->
            if (dataSnapshot.exists()) {
                val fullName = dataSnapshot.child("fullName").getValue(String::class.java)
                if (fullName != null) {
                    saveScanResult(fullName, uid)
                } else {
                    showError("Произошла ошибка! Некорректные данные о б администраторе.")
                }
            } else {
                showError("Произошла ошибка! Некорректный QR-код.")
            }
        }.addOnFailureListener {
            showError("Произошла ошибка при поиске администратора.")
        }
    }

    private fun saveScanResult(fullName: String, uid: String) {
        val currentDate = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date())
        val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        val historyReference = database.child("History").push()
        historyReference.child("date").setValue(currentDate)
        historyReference.child("fullName").setValue(fullName)
        historyReference.child("time").setValue(currentTime)
        historyReference.child("type").setValue("вход")
        historyReference.child("uid").setValue(uid)

        showSuccessMessage("Сканирование произведено успешно!")
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        finish()
    }

    private fun showSuccessMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        finish()
    }
}
