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
        database = FirebaseDatabase.getInstance().reference
        val scannedUid = intent.getStringExtra("scannedUid")

        if (scannedUid != null) {
            searchEmployee(scannedUid)
        } else {
            showMessage("Произошла ошибка! Некорректный QR-код.")
        }
    }

    private fun searchEmployee(uid: String) {
        database.child("employees").child(uid).get().addOnSuccessListener { dataSnapshot ->
            if (dataSnapshot.exists()) {
                val fullName = dataSnapshot.child("fullName").getValue(String::class.java)
                val status = dataSnapshot.child("status").getValue(Int::class.java) ?: 0
                if (fullName != null) {
                    if (status == 0){
                        saveScanResult(fullName, uid, 1)
                    } else {
                        saveScanResult(fullName, uid, 0)
                    }
                } else {
                    showMessage("Произошла ошибка! Некорректные данные о сотруднике.")
                }
            } else {
                showMessage("Произошла ошибка! Некорректный QR-код.")
            }
        }.addOnFailureListener {
            showMessage("Произошла ошибка при поиске сотрудника.")
        }
    }

    private fun saveScanResult(fullName: String, uid: String, newStatus: Int) {
        val currentDate = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date())
        val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        val actionType = if (newStatus == 1) "вход" else "выход"

        database.child("employees").child(uid).child("status").setValue(newStatus)

        val historyReference = database.child("History").push()
        historyReference.child("date").setValue(currentDate)
        historyReference.child("fullName").setValue(fullName)
        historyReference.child("time").setValue(currentTime)
        historyReference.child("type").setValue(actionType)
        historyReference.child("uid").setValue(uid)

        showMessage("Сканирование произведено успешно!")
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        finish()
    }
}
