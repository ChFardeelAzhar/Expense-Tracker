package com.example.expensetrackerfkyt.utils

import android.util.Patterns

// Function to check if email is valid
fun isValidEmail(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches() && email == email.lowercase() 
}

// Function to check if password is valid (at least 1 letter and 1 special character)
fun isValidPassword(password: String): Boolean {
    val containsLetter = password.any { it.isLetter() }
    val containsSpecialChar = password.any { !it.isLetterOrDigit() }
    return containsLetter && containsSpecialChar
}