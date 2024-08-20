@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.expensetrackerfkyt.screens.add_screen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.expensetrackerfkyt.R
import com.example.expensetrackerfkyt.data.model.ExpenseModelEntity
import com.example.expensetrackerfkyt.ui.theme.DarkSeeGreen
import com.example.expensetrackerfkyt.utils.DateUtils
import kotlinx.coroutines.launch


@Composable
fun AddScreen(
    addScreenViewModel: AddScreenViewModel = hiltViewModel(),
    navController: NavController,
    dataModel: ExpenseModelEntity?
    ) {

    val state = addScreenViewModel.state.observeAsState()
    val context = LocalContext.current
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {

        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {

            val (topBg, topBarSection, addDialog) = createRefs()

            Image(
                painter = painterResource(id = R.drawable.ic_topbarbg),
                contentDescription = "Top background",
                modifier = Modifier.constrainAs(topBg) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )

            TopBarAddScreen(modifier = Modifier.constrainAs(topBarSection) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })

            AddExpenseDataForm(
                modifier = Modifier
                    .padding(top = 30.dp, bottom = 130.dp)
                    .constrainAs(addDialog) {
                        top.linkTo(topBarSection.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                navController = navController,
                dataModel = dataModel

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
                Toast.makeText(context, "Item added successfully...", Toast.LENGTH_SHORT).show()
            }

            3 -> {
                Toast.makeText(context, "Item did not added successfully...!!", Toast.LENGTH_SHORT)
                    .show()

            }

        }


    }
}

@Composable
fun TopBarAddScreen(
    modifier: Modifier,
    ) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 60.dp, start = 16.dp, end = 16.dp)
    ) {


        Image(
            imageVector = Icons.Filled.KeyboardArrowLeft,
            contentDescription = null,
            modifier = Modifier.align(Alignment.CenterStart),
            colorFilter = ColorFilter.tint(Color.White)
        )

        Text(
            text = "Add Expense",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
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


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseDataForm(
    modifier: Modifier,
    viewModel: AddScreenViewModel = hiltViewModel(),
    dataModel: ExpenseModelEntity?,
    navController: NavController,
) {

    var expended = remember {
        mutableStateOf(false)
    }

    var showDialog = remember {
        mutableStateOf(false)
    }


    var title by remember {
        mutableStateOf(dataModel?.title ?: "")
    }

    var amount by remember {
        mutableStateOf(dataModel?.amount ?: "")
    }

    val typeOfData = listOf<String>("Income", "Expense")
    var selectedType = remember {
        mutableStateOf(
            if (dataModel?.type == "Income") {
                typeOfData[0]
            } else {
                typeOfData[1]
            } ?: typeOfData[0]
        )
    }

    var date = remember {
        mutableStateOf(
            dataModel?.date ?: System.currentTimeMillis()
        )
    }



    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .shadow(
                4.dp, RoundedCornerShape(16.dp),
                spotColor = DarkSeeGreen
            )
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .verticalScroll(rememberScrollState(), reverseScrolling = true)

    ) {

        Text(
            text = "Type",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.size(4.dp))

        OutlinedTextField(
            value = selectedType.value,
            onValueChange = {},
            colors = TextFieldDefaults.outlinedTextFieldColors(
                disabledTrailingIconColor = Color.Gray,
                disabledTextColor = MaterialTheme.colorScheme.onBackground,
                disabledBorderColor = Color.Gray
            ),
            readOnly = true,
            enabled = false,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expended.value)
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    expended.value = true
                },

            )


        Box(

        ) {
            ExpenseDropDown(
                expended = expended,
                typeOfData = typeOfData,
                onItemClick = {
                    selectedType.value = it
                    expended.value = false
                })
        }



        Spacer(modifier = Modifier.size(10.dp))

        Text(
            text = "Title",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.size(4.dp))
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.size(10.dp))


        Text(
            text = "Amount", fontSize = 14.sp, fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.size(4.dp))

        OutlinedTextField(
            value = amount.toString(),
            onValueChange = { amount = it },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )

        Spacer(modifier = Modifier.size(10.dp))

        Text(
            text = "Date", fontSize = 14.sp, fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.size(4.dp))
        OutlinedTextField(
            value = DateUtils.formatData(date.value),
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    showDialog.value = true
                },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                disabledTextColor = MaterialTheme.colorScheme.onBackground,
                disabledBorderColor = Color.Gray
            ),
            readOnly = true,
            enabled = false,
            trailingIcon = {
                Image(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = null,
                    colorFilter =
                    ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                )
            }
        )

        Spacer(modifier = Modifier.size(20.dp))

        Button(
            onClick = {

                /*
                if (id==null){
                    scope.launch {
                        viewModel.insertItem(
                            ExpenseModelEntity(
                                id = null,
                                type = selectedType.value,
                                title = title,
                                amount = amount.toString().toDouble(),
                                date = date.value
                            )
                        )
                    }
                }else{
                    scope.launch {
                        viewModel.updateItem(
                            ExpenseModelEntity(
                                id = id,
                                type = dataModel?.type ?: selectedType.value,
                                title = dataModel?.title!!,
                                amount = dataModel?.amount!!,
                                date = date.value
                            )
                        )
                    }

                 */

                scope.launch {
                    viewModel.storeData(
                        id = dataModel?.id,
                        title = title,
                        amount = amount.toString().toDouble(),
                        date = date.value,
                        typeOfData = selectedType.value
                    )
                }
                navController.popBackStack()
            },
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.onBackground),
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = DarkSeeGreen
            )
        ) {
            Text(
                text = "Add Expense",
                color = Color.White
            )
        }


    }

    if (showDialog.value) {
        ExpenseDatePickerDialog(
            selectedDate = { date.value = it },
            showDialog = showDialog
        )
    }

}

@Composable
fun ExpenseDropDown(
    expended: MutableState<Boolean>,
    typeOfData: List<String>,
    onItemClick: (item: String) -> Unit

) {

    DropdownMenu(
        expanded = expended.value,
        onDismissRequest = { expended.value = false },
        modifier = Modifier.width(295.dp)
    ) {

        typeOfData.forEach { item ->
            DropdownMenuItem(
                text = {
                    Text(
                        text = item,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                onClick = {
                    onItemClick(item)
                })
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseDatePickerDialog(
    selectedDate: (date: Long) -> Unit,
    showDialog: MutableState<Boolean>
) {
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis ?: System.currentTimeMillis()

    DatePickerDialog(
        onDismissRequest = { showDialog.value = false },
        confirmButton = {
            TextButton(onClick = {
                selectedDate(selectedDate)
                showDialog.value = false
            }) {
                Text(text = "Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = { showDialog.value = false }) {
                Text(text = "Cancle")
            }
        },
        modifier = Modifier.padding(16.dp)

    ) {
        DatePicker(state = datePickerState)
    }

}

