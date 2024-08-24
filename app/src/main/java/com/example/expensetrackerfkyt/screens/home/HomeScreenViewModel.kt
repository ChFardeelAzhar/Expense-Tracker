package com.example.expensetrackerfkyt.screens.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.expensetrackerfkyt.data.dao.ExpenseDao
import com.example.expensetrackerfkyt.data.model.ExpenseModelEntity
import com.example.expensetrackerfkyt.utils.formatCurrency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val expenseDao: ExpenseDao) : ViewModel() {

    val expenses : LiveData<List<ExpenseModelEntity>> = expenseDao.getAllExpenseData()
    val state = MutableStateFlow(1)

    fun totalBalance(list: List<ExpenseModelEntity>): String {


        var totalAmount = 0.0

        list.forEach { item ->

            if (item.type == "Income") {
                totalAmount += item.amount
            } else {
                totalAmount -= item.amount
            }

        }


        return formatCurrency(totalAmount)

    }

    fun totalIncome(list: List<ExpenseModelEntity>): String {

        var totalIncome = 0.0
        list.forEach { item ->
            if (item.type == "Income") {
                totalIncome += item.amount
            }
        }

        return formatCurrency(totalIncome)
    }

    fun totalExpense(list: List<ExpenseModelEntity>): String {

        var totalExpense = 0.0
        list.forEach { item ->
            if (item.type == "Expense") {
                totalExpense -= item.amount
            }
        }

        return formatCurrency(totalExpense)
    }

    suspend fun deleteItem(item: ExpenseModelEntity) {
        state.value = 0
        try {
            expenseDao.deleteExpense(item)
            state.value = 2
        } catch (e: Exception) {
            state.value = 3
            Log.d("delete", "Exception:  ${e.printStackTrace().toString()}")
        }

    }


}