package com.example.employee_access_control_system

import HistoryAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class EmployeeHistoryActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var recyclerView: RecyclerView
    private lateinit var button1: Button
    private lateinit var button2: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_history)

        auth = FirebaseAuth.getInstance()

        recyclerView = findViewById(R.id.recyclerView)
        button1 = findViewById(R.id.searchButton)
        button2 = findViewById(R.id.backButton)


        val currentUser: FirebaseUser? = auth.currentUser
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val historyRef: DatabaseReference = database.getReference("history")


        historyRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val historyList = mutableListOf<History>()
                    for (childSnapshot in snapshot.children) {
                        val history = childSnapshot.getValue(History::class.java)
                        history?.let {
                            historyList.add(it)
                        }
                    }
                    val adapter = HistoryAdapter(historyList)
                    recyclerView.adapter = adapter
                    recyclerView.layoutManager = LinearLayoutManager(this@EmployeeHistoryActivity)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })


        button1.setOnClickListener {
        }

        button2.setOnClickListener {
        }

    }
}