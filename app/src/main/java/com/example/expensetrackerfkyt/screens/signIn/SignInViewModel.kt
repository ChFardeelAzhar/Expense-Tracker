package com.example.expensetrackerfkyt.screens.signIn

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {


    val stateFlow = MutableStateFlow(1)
    fun signIn(
        email: String,
        password: String,
    ) {
        stateFlow.value = 0

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                stateFlow.value = 2 // success
            }
        }.addOnFailureListener {
            stateFlow.value = 3 // error
        }

    }

}