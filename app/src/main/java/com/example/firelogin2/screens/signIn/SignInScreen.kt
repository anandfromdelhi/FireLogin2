package com.example.firelogin2.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
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
import androidx.navigation.NavController
import com.example.firelogin2.navigation.Screens
import com.example.firelogin2.screens.signIn.SignInState

@Composable
fun SignInScreen(
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
            /*TODO*/
        }) {
            Text(text = "Sign in")
        }

        Button(onClick = { onSignInClick.invoke() }) {
            Text(text = "Sign in with google")
        }
        Spacer(modifier = Modifier.height(10.dp))

        Row(){
            Text(text = "New user ?")
            Spacer(modifier = Modifier.width(20.dp))
            Text(text = "Sign up", modifier = Modifier.clickable {
                navController.navigate(Screens.SignUpScreen.route)
            })
        }
    }
}