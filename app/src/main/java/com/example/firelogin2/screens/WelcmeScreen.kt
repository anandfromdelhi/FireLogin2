package com.example.firelogin2.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firelogin2.data.UserData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun WelcomeScreen(
    userData:UserData?,
    onSignOut: () -> Unit
) {
    val user = Firebase.auth.currentUser
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (userData?.profilePicture != null) {
            if (userData.userName != null) {
                Text(
                    text = userData.userName, textAlign = TextAlign.Center,
                    fontSize = 36.sp, fontWeight = FontWeight.SemiBold
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onSignOut) {
                Text(text = "Sign out")
            }
        }else{
            Text(text = "Welcome")
            if (user != null) {
                Text(text =user.email.toString() )
            } else {

                Text(text = "No user is signed in")
            }

        }
    }

}