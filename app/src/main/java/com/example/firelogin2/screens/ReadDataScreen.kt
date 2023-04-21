package com.example.firelogin2.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.firelogin2.data.UserData
import com.example.firelogin2.screens.viewmodels.CRUDViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

@Composable
fun ReadDataScreen(
    navController: NavController,
    crudViewModel: CRUDViewModel = CRUDViewModel(),
    userData: UserData? = null
) {
    val userThroughEmail = Firebase.auth.currentUser
    val userThroughGoogle = userData?.userName
    val user = userThroughEmail ?: userThroughGoogle
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        scope.launch {
            try {
                crudViewModel.retrieveData(context = context, uId = user.toString()) { data ->
                    name = data.uid
                    amount = data.amount
                    description = data.description
                }
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = name)
        Text(text = amount)
        Text(text = description)
    }
}