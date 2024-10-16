package com.example.expensetrackerfkyt.screens.signUp

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {


    val stateFlow = MutableStateFlow(1)
    fun signUp(
        name: String,
        email: String,
        password: String,
        confirmPassword: String,
    ) {
        stateFlow.value = 0

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                stateFlow.value = 2 // success
                Log.d("Frdl_Test", "Account Created Successfully: ")
            }
        }.addOnFailureListener {
            stateFlow.value = 3 // error
            Log.d("Frdl_Test", "Error: ${it.message.toString()}")
        }

    }

}