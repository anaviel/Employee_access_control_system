package com.example.employee_access_control_system

data class Employee(
    val id: String = "",
    val fullName: String = "",
    val phoneNumber: String = "",
    val position: String = "",
    val status: Int
) {
    // Пустой конструктор
    constructor() : this("", "", "", "", 0)
}

class Admin(
    val fullName: String = "",
    val position: String = "",
    val phoneNumber: String = ""
)

