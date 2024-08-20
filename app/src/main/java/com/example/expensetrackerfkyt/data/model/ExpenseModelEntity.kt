package com.example.expensetrackerfkyt.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expense_table")
data class ExpenseModelEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    val type: String,
    val title: String,
    val amount: Double,
    val date: Long

)

