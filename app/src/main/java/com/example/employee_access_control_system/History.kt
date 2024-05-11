package com.example.employee_access_control_system

data class History(
    val uid: String = "",
    var date: String = "",
    var employeeName: String = "",
    var timestamp: String = "",
    var type: String = ""
) {

    constructor() : this("", "", "", "", "")
}