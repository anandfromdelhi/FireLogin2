package com.example.firelogin2.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.firelogin2.data.UserData
import com.example.firelogin2.navigation.Screens
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun WelcomeScreen(
    userData:UserData?,
    onSignOut: () -> Unit,
    navController: NavController
) {
    val user = Firebase.auth.currentUser
    val context = LocalContext.current
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
                OutlinedButton(onClick = {
                    FirebaseAuth.getInstance().signOut()
                    navController.navigate(Screens.SignInScreen.route)
                    Toast.makeText(context,"Signed out successfully",Toast.LENGTH_SHORT).show()
                }) {
                    Text(text = "Sign out")
                }
            } else {

                Text(text = "No user is signed in")
            }


        }
        Button(onClick = {
            navController.navigate(Screens.AddEditDataScreen.route)
        }) {
            Text(text = "Add")
        }
    }

}