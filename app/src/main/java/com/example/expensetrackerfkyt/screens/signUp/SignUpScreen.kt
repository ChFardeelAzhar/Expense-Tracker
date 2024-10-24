package com.example.expensetrackerfkyt.screens.signUp

import android.annotation.SuppressLint
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.expensetrackerfkyt.R
import com.example.expensetrackerfkyt.ui.theme.DarkSeeGreen
import com.example.expensetrackerfkyt.utils.NavRouts
import com.example.expensetrackerfkyt.utils.isValidEmail
import com.example.expensetrackerfkyt.utils.isValidPassword

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: SignUpViewModel = hiltViewModel()
) {


    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var confirmPasswordError by remember { mutableStateOf(false) }

    var showDialog by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    val state = viewModel.stateFlow.collectAsState()
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {

        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (topBg, textFieldSection) = createRefs()
            Image(
                painter = painterResource(id = R.drawable.ic_topbarbg),
                contentDescription = "Top background",
                modifier = Modifier.constrainAs(topBg) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(textFieldSection) {
                        top.linkTo(topBg.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(top = 50.dp, start = 12.dp, end = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Image(
                    painter = painterResource(id = R.drawable.login_screen_logo),
                    contentDescription = "Top background",
                    modifier = Modifier.size(100.dp)
                )
                Text(
                    text = "Sign Up",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.size(20.dp))

                Text(
                    text = "Name", fontSize = 14.sp, fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )

                Spacer(modifier = Modifier.size(4.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    colors = TextFieldDefaults.colors(
                        cursorColor = DarkSeeGreen,
                        focusedIndicatorColor = DarkSeeGreen,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    ),
                )

                Spacer(modifier = Modifier.size(10.dp))

                Text(
                    text = "Email", fontSize = 14.sp, fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.size(4.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    colors = TextFieldDefaults.colors(
                        cursorColor = DarkSeeGreen,
                        focusedIndicatorColor = DarkSeeGreen,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    )
                )
                if (emailError) {
                    Text(
                        text = "Invalid email format", color = MaterialTheme.colorScheme.error,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp)
                            .background(color = MaterialTheme.colorScheme.error),
                        textAlign = TextAlign.Start
                    )
                }
                Spacer(modifier = Modifier.size(10.dp))

                Text(
                    text = "Password", fontSize = 14.sp, fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )

                Spacer(modifier = Modifier.size(4.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    colors = TextFieldDefaults.colors(
                        cursorColor = DarkSeeGreen,
                        focusedIndicatorColor = DarkSeeGreen,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    )
                )
                if (passwordError) {
                    Text(
                        text = "Password must contain at least 1 letter and 1 special character",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp)
                            .background(color = MaterialTheme.colorScheme.error),
                        textAlign = TextAlign.Start
                    )
                }

                Spacer(modifier = Modifier.size(10.dp))

                Text(
                    text = "Confirm Password", fontSize = 14.sp, fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )

                Spacer(modifier = Modifier.size(4.dp))

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = {
                        confirmPassword = it

                    },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.clearFocus()
                        }
                    ),
                    colors = TextFieldDefaults.colors(
                        cursorColor = DarkSeeGreen,
                        focusedIndicatorColor = DarkSeeGreen,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    )
                )
                if (confirmPasswordError) {
                    Text(
                        text = "Passwords do not match",
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp)
                            .background(color = MaterialTheme.colorScheme.error),
                        textAlign = TextAlign.Start
                    )
                }
                Spacer(modifier = Modifier.size(20.dp))

                Button(
                    onClick = {
                        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                            Toast.makeText(context, "Please enter the correct email", Toast.LENGTH_SHORT).show()
                        }
                        else if (password.length < 6 && confirmPassword.length < 6) {
                            Toast.makeText(
                                context,
                                "Password should contain at least 6 words",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        else if (password != confirmPassword){
                            Toast.makeText(context, "Password not match", Toast.LENGTH_SHORT).show()
                        }
                        else {
                            viewModel.signUp(name, email, password, confirmPassword)
                        }
                    },
                    enabled = !name.isNullOrEmpty() && !email.isNullOrEmpty() && !password.isNullOrEmpty() && !confirmPassword.isNullOrEmpty(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DarkSeeGreen
                    )
                ) {
                    Text(text = "Sign Up", color = MaterialTheme.colorScheme.onBackground)
                }

                Spacer(modifier = Modifier.size(20.dp))

                TextButton(onClick = {
                    navController.navigate(NavRouts.Destination.SignIn.route) {
                        launchSingleTop = true
                    }
                }, modifier = Modifier.padding(bottom = 20.dp)) {
                    Text(text = "Already a User -> Go to Sign In ")

                }
            }

            when (state.value) {
                0 -> {
                    showDialog = true
                }

                1 -> {

                }

                2 -> {
                    Toast.makeText(context, "Account Created Successfully", Toast.LENGTH_SHORT)
                        .show()
                    navController.navigate(NavRouts.Destination.HomeScreen.route) {
                        launchSingleTop = true
                    }
                }

                3 -> {
                    Toast.makeText(
                        context,
                        "Something went wrong please try again later!",
                        Toast.LENGTH_SHORT
                    ).show()
                    showDialog = false
                }
            }

            if (showDialog) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = DarkSeeGreen)
                }
            }

        }
    }


}