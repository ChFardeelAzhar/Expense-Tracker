package com.example.expensetrackerfkyt.auth

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _isUserLogin: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val isUserLogin: LiveData<Boolean> = _isUserLogin

    private val _userName: MutableLiveData<String?> = MutableLiveData<String?>()
    val userName: LiveData<String?> = _userName


    init {
        checkUserLoginStatus()
    }

    private fun checkUserLoginStatus() {
        val user = auth.currentUser
        _isUserLogin.value = user != null
        _userName.value = user?.displayName

        Log.d("CHECK_USER", "USER NAME :${_userName.value} ")

    }

    fun logout(){
        auth.signOut()
        _isUserLogin.value = false
    }


}