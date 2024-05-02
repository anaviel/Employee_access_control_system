package com.example.employee_access_control_system

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class HistoryActivity : AppCompatActivity(), HistoryAdapter.HistoryItemClickListener {

    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var historyList: MutableList<History>
    private lateinit var historyRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        // Инициализация RecyclerView и HistoryAdapter
        val recyclerView = findViewById<RecyclerView>(R.id.historyRecyclerView)
        historyList = mutableListOf()
        historyAdapter = HistoryAdapter(historyList)
        recyclerView.adapter = historyAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Получение ссылки на базу данных Firebase
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        historyRef = database.getReference("history")

        // Добавление слушателя для получения данных из Firebase
        historyRef.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val history = snapshot.getValue(History::class.java)
                history?.let {
                    historyList.add(it)
                    historyAdapter.notifyItemInserted(historyList.size - 1)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                // Обработка изменений существующих элементов, если необходимо
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                // Обработка удаления элементов, если необходимо
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                // Обработка перемещения элементов, если необходимо
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("HistoryActivity", "Ошибка базы данных: ${error.message}")
            }
        })
    }

    override fun onItemClick(history: History) {
        // Обработка нажатия на элемент RecyclerView, если необходимо
    }
}
