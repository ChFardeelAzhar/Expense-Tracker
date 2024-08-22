package com.example.expensetrackerfkyt.screens.stats

import androidx.lifecycle.ViewModel
import com.example.expensetrackerfkyt.data.dao.ExpenseDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatsScreenViewModel @Inject constructor(private val dao: ExpenseDao) : ViewModel() {

    val expenses = dao.getAllExpenseData()



}