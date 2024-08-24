package com.example.expensetrackerfkyt.screens.stats

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.expensetrackerfkyt.R
import com.example.expensetrackerfkyt.screens.BottomBarItemData
import com.example.expensetrackerfkyt.screens.BottomExpenseBar
import com.example.expensetrackerfkyt.screens.BottomNavigationBar
import com.example.expensetrackerfkyt.utils.NavRouts

@Composable
fun StatsScreen(
    navController: NavController
) {

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
            ) {

                Image(
                    imageVector = Icons.Filled.KeyboardArrowLeft,
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterStart),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                )

                Text(
                    text = "Statistics",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .align(Alignment.Center)
//                .padding(16.dp)
                )

                Image(
                    painter = painterResource(id = R.drawable.ic_dots),
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
        },
        bottomBar = {


            val backStackEntry = navController.currentBackStackEntryAsState()

            val isHomeSelected =
                NavRouts.Destination.HomeScreen.route == backStackEntry.value?.destination?.route
            val isStatsSelected =
                NavRouts.Destination.StatsScreen.route == backStackEntry.value?.destination?.route

            val itemsList = listOf<BottomBarItemData>(
                BottomBarItemData(
                    route = NavRouts.Destination.HomeScreen.route,
                    image = if (isHomeSelected) R.drawable.ic_filled_home else R.drawable.ic_outlined_home
                ),
                BottomBarItemData(
                    route = NavRouts.Destination.StatsScreen.route,
                    image = if (isStatsSelected) R.drawable.ic_filled_barchart else R.drawable.ic_outlined_barchart
                ),

                )

            BottomNavigationBar(
                navController = navController,
                items = itemsList,
                onItemClick = { item ->
                    navController.navigate(item.route) {
                        popUpTo(item.route)
                        launchSingleTop = true
                    }
                })


//            BottomExpenseBar(
//                items = itemsList,
//                onImageClick = {
//                    navController.navigate(it.route) {
//                        popUpTo(item.route)
//                        launchSingleTop = true
//                    }
//                }
//            )

        },

        ) {
        Column(
            modifier = Modifier.padding(it)
        ) {


        }
    }
}

@Composable
fun LineChart() {

}
