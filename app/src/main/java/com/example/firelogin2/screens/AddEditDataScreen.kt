package com.example.firelogin2.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavController
import com.example.firelogin2.data.MoneyData
import com.example.firelogin2.data.UserData
import com.example.firelogin2.navigation.Screens
import com.example.firelogin2.screens.viewmodels.CRUDViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun AddEditDataScreen(
    navController: NavController,
    crudViewModel: CRUDViewModel = CRUDViewModel(),
    userData: UserData? = null
) {
    val user = Firebase.auth.currentUser
    var name by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val context = LocalContext.current
    name = userData?.userName ?: user?.email.toString()
    val scope = rememberCoroutineScope()


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(value = amount,
            onValueChange = { amount = it },
            label = { Text(text = amount) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text(text = "description") })
        Button(onClick = {
            scope.launch(Dispatchers.Main) {
                try {
                    val moneyData = MoneyData(uid = name, amount, description)
                    crudViewModel.saveData(moneyData, context)
                    navController.navigate(Screens.ReadDataScreen.route)
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                }
            }

        }) {
            Text(text = "Add")
        }
    }
}