package com.example.expensetrackerfkyt.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.expensetrackerfkyt.data.model.ExpenseModelEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {

    @Insert
    suspend fun addExpense(item : ExpenseModelEntity)


    @Delete
    suspend fun deleteExpense(item : ExpenseModelEntity)

    @Update
    suspend fun updateExpense(item : ExpenseModelEntity)

    @Query("SELECT * FROM expense_table ORDER BY id DESC")
    fun getAllExpenseData() : Flow<List<ExpenseModelEntity>>

    @Query("SELECT * FROM expense_table WHERE id = :id")
    fun getItemById(id : Long) : ExpenseModelEntity

}