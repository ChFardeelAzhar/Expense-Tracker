package com.example.expensetrackerfkyt.screens.add_screen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.expensetrackerfkyt.data.dao.ExpenseDao
import com.example.expensetrackerfkyt.data.model.ExpenseModelEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.Exception

@HiltViewModel
class AddScreenViewModel @Inject constructor(private val dao: ExpenseDao) : ViewModel() {


    val state = MutableLiveData(1)
    val newState = MutableStateFlow(1)
    fun storeData(
        id: Long? = null,
        typeOfData: String,
        title: String,
        amount: Double,
        date: Long
    ) {
        state.postValue(0)
        newState.value = 0
        CoroutineScope(Dispatchers.IO).launch {
            val data = ExpenseModelEntity(
                id = id,
                type = typeOfData,
                title = title,
                amount = amount,
                date = date
            )

            try {
                if (id != null) {
                    dao.updateExpense(data)
                    newState.value = 4
                    state.postValue(4)// item updated successfully
                } else {
                    dao.addExpense(data)
                    newState.value = 2
                    state.postValue(2) // item inserted successfully
                }
            } catch (e: Exception) {
                Log.d("TAG", "storeData: ${e.message}")
                newState.value = 3
                state.postValue(3) // something went wrong
            }
        }


    }

    suspend fun getItemById(id: Long): ExpenseModelEntity {
        return dao.getItemById(id)
    }


}