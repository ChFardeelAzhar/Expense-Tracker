package com.example.expensetrackerfkyt.viewmodel

import androidx.lifecycle.ViewModel
import com.example.expensetrackerfkyt.data.dao.ExpenseDao
import com.example.expensetrackerfkyt.data.model.ExpenseModelEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor( private val dao: ExpenseDao) : ViewModel() {

    fun getItemById(id : Long) : ExpenseModelEntity {
        return dao.getItemById(id)
    }

}