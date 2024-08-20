package com.example.expensetrackerfkyt

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.expensetrackerfkyt.screens.add_screen.AddScreen
import com.example.expensetrackerfkyt.screens.home_screen.MainScreen
import com.example.expensetrackerfkyt.screens.home_screen.HomeScreenViewModel
import com.example.expensetrackerfkyt.ui.theme.ExpenseTrackerFKYTTheme
import com.example.expensetrackerfkyt.utils.NavRouts
import com.example.expensetrackerfkyt.viewmodel.MainScreenViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExpenseTrackerFKYTTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationSection()
                }

            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationSection(viewModel: MainScreenViewModel = hiltViewModel()) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRouts.Destination.HomeScreen.route
    ) {

        composable(NavRouts.Destination.HomeScreen.route){
            MainScreen(navController = navController)
        }


        composable(
            route = NavRouts.Destination.AddScreen.route,
        ){

            val data = it.arguments?.getString("{id}", "-1")

            Log.d("mySelectedId", "ID : ${data.toString()}")
            val dataModel = if (data != "-1"){
                viewModel.getItemById(data!!.toLong())
            }else{
                null
            }
            AddScreen(
                navController = navController,
                dataModel = dataModel
            )
        }

    }

}


