package com.example.expensetrackerfkyt.screens.add_screen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.expensetrackerfkyt.data.dao.ExpenseDao
import com.example.expensetrackerfkyt.data.model.ExpenseModelEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.Exception

@HiltViewModel
class AddScreenViewModel @Inject constructor(private val dao: ExpenseDao) : ViewModel() {

    val state = MutableLiveData(1)  // default value

    fun storeData(
        id: Long? = null,
        typeOfData: String,
        title: String,
        amount: Double,
        date: Long
    ) {
        state.postValue(0)

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
                } else {
                    dao.addExpense(data)
                }
                state.postValue(2)
            } catch (e: Exception) {
                state.postValue(3)
                Log.d("TAG", "storeData: ${e.message}")
            }
        }


    }

    suspend fun getItemById(id : Long) : ExpenseModelEntity {
        return dao.getItemById(id)
    }

    /*

    suspend fun insertItem(item: ExpenseModelEntity) {

        state.postValue(0) // loading
        try {
            dao.addExpense(item)
            state.postValue(2) // success
        } catch (e: Exception) {
            state.postValue(3) // error
        }

    }

    suspend fun updateItem(item: ExpenseModelEntity) {
        state.postValue(0) // loading

        try {
            dao.updateExpense(item)
            state.postValue(2) // success case
        } catch (e: Exception) {
            Log.d("onUpdateError", "Exception: $e")
            state.postValue(3) // error case
        }
    }
     */


}