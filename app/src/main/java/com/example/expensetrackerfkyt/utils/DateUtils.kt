package com.example.expensetrackerfkyt.utils

import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Locale

object DateUtils {
    fun formatDate(date: Long): String {
        val formatter = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
        return formatter.format(date)
    }

    fun dateTimeFormatter(date: Long) : String{
        val dateTimeFormatter = SimpleDateFormat("dd-MMM-yyyy,  HH:mm a", Locale.getDefault())
        return dateTimeFormatter.format(date)
    }


}