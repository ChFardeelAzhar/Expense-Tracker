package com.example.expensetrackerfkyt.utils

object NavRouts {

    sealed class Destination(val route : String){
        object HomeScreen : Destination("home_screen")
        object AddScreen : Destination("add_screen/id={id}")
    }


}