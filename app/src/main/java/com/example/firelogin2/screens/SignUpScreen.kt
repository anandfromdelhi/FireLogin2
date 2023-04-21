package com.example.firelogin2.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.firelogin2.navigation.Screens
import com.example.firelogin2.data.AuthUser
import com.example.firelogin2.screens.viewmodels.AuthViewModel
import com.example.firelogin2.utils.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var retypePassword by remember { mutableStateOf("") }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    fun alert(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
    var state by remember {
        mutableStateOf(false)
    }
    if (state) {
        Dialog(onDismissRequest = { }) {
            CircularProgressIndicator()
        }
    }

    var validPassword by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email") })
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = retypePassword,
            onValueChange = { retypePassword = it },
            label = { Text(text = "Retype password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedButton(onClick = {
            email = email.trim()
            password = password.trim()
            retypePassword = retypePassword.trim()
            if (password == retypePassword) {
                validPassword = true
            }else{
                alert("retyped password don't match")
            }

            if (email.isNotEmpty() && password.isNotEmpty() && validPassword) {
                scope.launch(Dispatchers.Main) {
                    viewModel.createUser(AuthUser(email, password)).collect {
                        when (it) {
                            is ResultState.Success -> {
                                state = false
                                alert("Signed up successfully")
                                navController.navigate(Screens.WelcomeScreen.route)
                            }
                            is ResultState.Failure -> {
                                state = false
                                val message = it.msg.toString().split(":")
                                alert(message[1])
                            }
                            is ResultState.Loading -> {
                                state = true
                            }
                        }
                    }
                }
            }

        }) {
            Text(text = "Sign up")
        }
    }

}