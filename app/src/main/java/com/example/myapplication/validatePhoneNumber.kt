package com.example.myapplication


fun validatePhoneNumber(number: String): Boolean {
    return number.matches(Regex("^(\\+91)?[6-9][0-9]{9}\$"))
}