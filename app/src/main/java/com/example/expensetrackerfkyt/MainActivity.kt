package com.example.expensetrackerfkyt

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.expensetrackerfkyt.screens.add_screen.AddScreen
import com.example.expensetrackerfkyt.screens.home.MainScreen
import com.example.expensetrackerfkyt.screens.stats.StatsScreen
import com.example.expensetrackerfkyt.ui.theme.DarkSeeGreen
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
fun NavigationSection() {

    val navController = rememberNavController()

    Scaffold(
    ) {

        NavHost(
            navController = navController,
            startDestination = NavRouts.Destination.HomeScreen.route,
            modifier = Modifier.padding(it)
        ) {

            composable(NavRouts.Destination.HomeScreen.route) {
                MainScreen(navController = navController)
            }


            composable(
                route = NavRouts.Destination.AddScreen.route,
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.StringType
//                    defaultValue = "-1"
                    }
                )
            ) {

                val data = it.arguments?.getString("id", "-1")

                Log.d("mySelectedId", "ID : ${data.toString()}")


                AddScreen(
                    navController = navController,
                    dataModelId = data
                )
            }

            composable(NavRouts.Destination.StatsScreen.route) {
                StatsScreen(
                navController = navController
                )
            }

        }

    }


}



