package com.example.employee_access_control_system

data class History(
    val id: String = "",
    val uid: String = "",
    var date: String = "",
    var fullName: String = "",
    var time: String = "",
    var type: String = ""
) {

    constructor() : this("", "", "", "", "", "")
}
