package com.example.firelogin2.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.firelogin2.navigation.Screens
import com.example.firelogin2.repo.AuthUser
import com.example.firelogin2.screens.signIn.SignInState
import com.example.firelogin2.screens.signIn.SignInViewModel
import com.example.firelogin2.utils.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    signInState: SignInState,
    onSignInClick: () -> Unit,
    navController: NavController
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        val context = LocalContext.current
        LaunchedEffect(key1 = signInState.signInError) {
            signInState.signInError?.let { error ->
                Toast.makeText(context, error, Toast.LENGTH_LONG).show()
            }
        }


        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        val scope = rememberCoroutineScope()
        fun alert(msg: String) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }

        var state by remember { mutableStateOf(false) }

        if (state) {
            Dialog(onDismissRequest = { /*TODO*/ }) {
                CircularProgressIndicator()
            }
        }

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
        OutlinedButton(onClick = {
            scope.launch(Dispatchers.Main) {
                email = email.trim()
                password = password.trim()
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    viewModel.loginUser(AuthUser(email, password)).collect {
                        when (it) {
                            is ResultState.Success -> {
                                state = false
                                alert("Signed in successfully")
                                navController.navigate(Screens.WelcomeScreen.route)
                            }
                            is ResultState.Failure -> {
                                state = false
                                val message = it.msg.toString().split(":")
                                alert(message[1])
                            }
                            is ResultState.Loading -> {state = true}
                        }
                    }
                }
            }
        }) {
            Text(text = "Sign in")
        }

        Button(onClick = {
            email = email.trim()
            password = password.trim()
            onSignInClick.invoke()
        }) {
            Text(text = "Sign in with google")
        }
        Spacer(modifier = Modifier.height(10.dp))

        Row() {
            Text(text = "New user ?")
            Spacer(modifier = Modifier.width(20.dp))
            Text(text = "Sign up", modifier = Modifier.clickable {
                navController.navigate(Screens.SignUpScreen.route)
            })
        }
    }
}