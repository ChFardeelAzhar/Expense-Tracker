package com.example.expensetrackerfkyt.utils

import java.text.SimpleDateFormat
import java.util.Locale

object DateUtils {
    fun formatData(date: Long): String {
        val formatter = SimpleDateFormat("dd MMMM yyyy, HH:mm a", Locale.getDefault())
        return formatter.format(date)
    }
}