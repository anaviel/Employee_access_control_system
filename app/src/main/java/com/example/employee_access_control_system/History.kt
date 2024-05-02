package com.example.employee_access_control_system

data class History(
    val id: String = "",
    val timestamp: String = "",
    val type: String = "",
    val employeeName: String = ""
) {

    constructor() : this("", "", "", "")
}