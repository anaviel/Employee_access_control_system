package com.example.employee_access_control_system

data class History(
    val uid: String = "",
    val date: String = "",
    val employeeName: String = "",
    val timestamp: String = "",
    val type: String = ""
) {

    constructor() : this("", "", "", "", "")
}