package com.example.expensetrackerfkyt.screens.detail_screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.expensetrackerfkyt.R
import com.example.expensetrackerfkyt.data.model.ExpenseModelEntity
import com.example.expensetrackerfkyt.screens.home.SingleHistoryItem
import com.example.expensetrackerfkyt.ui.theme.DarkSeeGreen
import com.example.expensetrackerfkyt.ui.theme.Green
import com.example.expensetrackerfkyt.utils.NavRouts
import com.google.android.play.core.integrity.r
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailScreen(
    navController: NavController,
    detailScreenViewModel: DetailScreenViewModel = hiltViewModel()
) {

    val allHistoryItems =
        detailScreenViewModel.transactionList.observeAsState(initial = emptyList())
    val state = detailScreenViewModel.state.value

    var showDialog = remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var itemToDelete by remember { mutableStateOf<ExpenseModelEntity?>(null) }

    if (showDialog.value && itemToDelete != null) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text(text = "Delete Item", fontSize = 16.sp) },
            text = { Text("Are you sure you want to delete this item?") },
            confirmButton = {
                TextButton(onClick = {
                    itemToDelete?.let {
                        scope.launch {
                            detailScreenViewModel.deleteItem(it)
                        }
                    }
                    itemToDelete = null
                    showDialog.value = false
                }) {
                    Text("yes", color = MaterialTheme.colorScheme.onBackground)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDialog.value = false
                    itemToDelete = null
                }) {
                    Text("No", color = MaterialTheme.colorScheme.onBackground)
                }
            }
        )
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Transaction History", style = MaterialTheme.typography.titleLarge)
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = DarkSeeGreen,
                    titleContentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(align = Alignment.CenterVertically),
                navigationIcon = {
                    Image(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = Modifier.clickable {
                            navController.popBackStack()
                        }.padding(end = 5.dp)
                    )
                }
            )
        }
    ) { paddingValues ->

        if (allHistoryItems.value.isNullOrEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = R.drawable.login_screen_logo),
                    contentDescription = null
                )
            }
        } else {

            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 80.dp)
                    .fillMaxSize()
            ) {
                items(allHistoryItems.value, key = { it.id.toString() }) { item ->
                    SingleHistoryItem(

                        color = if (item.type == "Expense") Color.Red else Green,
                        item = item,
                        onLongPress = {
                            itemToDelete = item
                            showDialog.value = true
                        },
                        onUpdate = {
                            val route =
                                NavRouts.Destination.AddScreen.route.replace("{id}", it.toString())
                            navController.navigate(route)
                        }
                    )
                }


            }
        }


    }

    when (state) {
        0 -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = DarkSeeGreen, strokeCap = StrokeCap.Round)
            }
        }

        2 -> {
            LaunchedEffect(key1 = true) {
                Toast.makeText(context, "Item deleted successfully...", Toast.LENGTH_SHORT).show()
            }
        }

        3 -> {
            LaunchedEffect(key1 = true) {
                Toast.makeText(context, "Failed to delete item!", Toast.LENGTH_SHORT).show()
            }
        }
    }


}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun Preview() {

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) {

    }

}