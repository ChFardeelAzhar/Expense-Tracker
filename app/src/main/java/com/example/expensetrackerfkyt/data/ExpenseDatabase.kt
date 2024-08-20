package com.example.expensetrackerfkyt.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.expensetrackerfkyt.data.dao.ExpenseDao
import com.example.expensetrackerfkyt.data.model.ExpenseModelEntity

@Database(entities = [ExpenseModelEntity::class], version = 1, exportSchema = false)
abstract class ExpenseDatabase : RoomDatabase() {
    abstract fun getDao() : ExpenseDao
}