package com.example.expensetrackerfkyt.screens.detail_screen

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.expensetrackerfkyt.data.dao.ExpenseDao
import com.example.expensetrackerfkyt.data.model.ExpenseModelEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val expenseDao: ExpenseDao
) : ViewModel() {

    val transactionList: LiveData<List<ExpenseModelEntity>> = expenseDao.getAllExpenseData()

    //    private fun getAllTransactionList() : List<ExpenseModelEntity> {
//
//    }
    
    val state = MutableStateFlow(1)

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