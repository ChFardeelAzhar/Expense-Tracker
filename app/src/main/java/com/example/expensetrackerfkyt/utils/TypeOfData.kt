package com.example.expensetrackerfkyt.utils


sealed class TypeOfData(type: String) {
    object Income : TypeOfData("Income")
    object Expense : TypeOfData("Expense")
}
