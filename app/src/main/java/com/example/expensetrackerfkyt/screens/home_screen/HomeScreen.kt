package com.example.expensetrackerfkyt.screens.home_screen

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.expensetrackerfkyt.R
import com.example.expensetrackerfkyt.data.model.ExpenseModelEntity
import com.example.expensetrackerfkyt.ui.theme.DarkSeeGreen
import com.example.expensetrackerfkyt.utils.DateUtils
import com.example.expensetrackerfkyt.utils.NavRouts
import com.example.expensetrackerfkyt.utils.formatCurrency
import kotlinx.coroutines.launch
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    navController: NavController
) {

    val scope = rememberCoroutineScope()
    val state = viewModel.state.observeAsState()
    val context = LocalContext.current

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(NavRouts.Destination.AddScreen.route) {
                        launchSingleTop = true
                    }
                },
                containerColor = DarkSeeGreen,
                modifier = Modifier.padding(16.dp)
            ) {
                Image(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    ) {

        Surface(
            modifier = Modifier.fillMaxSize()
        ) {

            ConstraintLayout(
                modifier = Modifier.fillMaxSize()
            ) {

                val (topAppBar, greetingSection, card, transactionHistory) = createRefs()

                Image(
                    painter = painterResource(id = R.drawable.ic_topbarbg),
                    contentDescription = null,
                    modifier = Modifier.constrainAs(topAppBar) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                )



                GreetingSection(
                    modifier = Modifier
                        .padding(
                            top = 30.dp,
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 16.dp
                        )
                        .constrainAs(greetingSection) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )


                val state = viewModel.expenses.collectAsState(initial = emptyList())
                val totalBalance = viewModel.totalBalance(state.value)
                val income = viewModel.totalIncome(state.value)
                val expense = viewModel.totalExpense(state.value)

                CardItem(
                    modifier = Modifier.constrainAs(card) {
                        top.linkTo(greetingSection.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                    totalBalance = totalBalance,
                    income = income,
                    expense = expense,
                )

                HistorySection(
                    items = state.value,
                    modifier = Modifier
                        .padding(
                            bottom = 200.dp
                        )
                        .constrainAs(transactionHistory) {
                            top.linkTo(card.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)

                        },
                    onDelete = {
                        scope.launch {
                            viewModel.deleteItem(it)
                        }
                    },
                    navController = navController
                )

            }

            when (state.value) {
                0 -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                1 -> {

                }

                2 -> {

                    Toast.makeText(context, "Item deleted successfully...", Toast.LENGTH_SHORT)
                        .show()
                }

                3 -> {

                    Toast.makeText(
                        context,
                        "Item did not delete successfully...!!",
                        Toast.LENGTH_SHORT
                    ).show()

                }

            }


        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GreetingSection(modifier: Modifier) {

    val currentTime = LocalTime.now()

    val currentGreeting = if (currentTime.isBefore(LocalTime.NOON)) {
        "Good Morning!"
    } else if (currentTime.isBefore(LocalTime.of(17, 0))) {
        "Good Afternoon!"
    } else {
        "Good Evening!"

    }

    /*
    val currentHours = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

    val greeting = if (currentHours < 12) {
        "Good Morning!"
    } else if (currentHours < 17) {
        "Good AfterNoon!"
    } else "Good Evening!"

     */

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {

        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = currentGreeting, fontSize = 16.sp, color = Color.White)
            Text(
                text = "Fardeel Azhar",
                fontSize = 22.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        Image(
            painter = painterResource(id = R.drawable.ic_notification),
            contentDescription = null,
        )

    }
}


data class SingleCardItemData(
    val image: Int,
    val type: String,
    val amount: String
)

@Composable
fun CardItem(
    modifier: Modifier,
    income: String,
    expense: String,
    totalBalance: String

) {

    ElevatedCard(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp)),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            DarkSeeGreen
        )

    ) {

        Column(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(10.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "Total Balance", fontSize = 16.sp, color = Color.White)
                    Text(
                        text = totalBalance,
                        fontSize = 22.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.ic_dots),
                    contentDescription = null,
                )
            }

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                SingleCardItem(
                    item = SingleCardItemData(
                        image = R.drawable.ic_expense,
                        type = "Expense",
                        amount = expense
                    ),
                    color = Color.Red
                )

                SingleCardItem(
                    color = Color.Green,
                    item = SingleCardItemData(
                        image = R.drawable.ic_income_final,
                        type = "Income",
                        amount = income
                    )
                )

            }
        }

    }

}

@Composable
fun SingleCardItem(
    item: SingleCardItemData,
    color: Color
) {

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,

        ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(
                        color,
                        CircleShape
                    )
            ) {
                Image(
                    painter = painterResource(id = item.image),
                    contentDescription = item.type,
                    modifier = Modifier
                        .clip(CircleShape)
                        .padding(2.dp),

                    )
            }
            Spacer(modifier = Modifier.size(5.dp))
            Text(
                text = item.type,
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.ExtraBold
            )

        }


        Text(
            text = "${item.amount}",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White

        )

    }
}


@Composable
fun HistorySection(
    modifier: Modifier,
    items: List<ExpenseModelEntity>,
    onDelete: (ExpenseModelEntity) -> Unit,
    navController: NavController
) {

    var showDialog = remember {
        mutableStateOf(false)
    }
    var itemToDelete by remember { mutableStateOf<ExpenseModelEntity?>(null) }

    if (showDialog.value && itemToDelete != null) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text(text = "Delete Item", fontSize = 16.sp) },
            text = { Text("Are you sure you want to delete this item?") },
            confirmButton = {
                TextButton(onClick = {
                    itemToDelete?.let { onDelete(it) }
                    showDialog.value = false
                }) {
                    Text("Delete", color = MaterialTheme.colorScheme.onBackground)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog.value = false }) {
                    Text("Cancel", color = MaterialTheme.colorScheme.onBackground)
                }
            }
        )
    }


    LazyColumn(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
    ) {

        item {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            ) {

                Text(
                    text = "Recent Transaction",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                )

                Text(
                    text = "See all",
                    fontSize = 14.sp,
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .clickable {

                        }
                        .padding(3.dp)
                )

            }
        }

        if (items.isEmpty()) {

            item {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Image(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = null,
                        modifier = Modifier.size(30.dp),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                    )
                }
            }
        } else {

            items(items) { item ->
                SingleHistoryItem(
                    color = if (item.type == "Expense") Color.Red else Color.Green,
                    item = item,
                    onLongPress = {
                        itemToDelete = it
                        showDialog.value = true
                    },
                    onUpdate = {

                        Log.d("currentId", "MyCurrentItemId: ${it.toString()}")
                        val route = NavRouts.Destination.AddScreen.route.replace("{id}",it.toString())
//                        val route = NavRouts.Destination.AddScreen.route + "/${it}"
                        navController.navigate(route)
                    }
                )
            }
        }


    }


}

@Composable
fun SingleHistoryItem(
    color: Color,
    item: ExpenseModelEntity,
    onLongPress: (ExpenseModelEntity) -> Unit,
    onUpdate: (id: Long) -> Unit
) {

    var swipeOffset by remember {
        mutableStateOf(0f)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .pointerInput(Unit) {
//                detectTapGestures(
//                    onLongPress = {
//                        onLongPress(item)
//                    }
//                )

                detectHorizontalDragGestures { _, dragAmount ->
                    swipeOffset += dragAmount
                    if (swipeOffset < -100f) {
                        onLongPress(item)
                    }
                }
            }
            .clickable {
                onUpdate.invoke(item.id!!)
            }
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {

            Text(text = item.title, fontSize = 16.sp)
            Text(
                text = DateUtils.formatData(item.date),
                fontSize = 12.sp,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Thin
            )

        }

        Text(
            text = if (item.type == "Expense") "-${formatCurrency(item.amount)}" else "+${
                formatCurrency(
                    item.amount
                )
            }",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )

    }
}
