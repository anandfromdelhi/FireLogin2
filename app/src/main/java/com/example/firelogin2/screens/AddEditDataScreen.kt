package com.example.firelogin2.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.firelogin2.screens.viewmodels.CRUDViewModel

@Composable
fun AddEditDataScreen(navController: NavController,crudViewModel: CRUDViewModel){

    var amount by remember{ mutableStateOf("") }
    var description by remember{ mutableStateOf("") }
    val context = LocalContext.current

    Column() {
        OutlinedTextField(value = amount, onValueChange ={amount = it} )
        OutlinedTextField(value = description , onValueChange = {description = it})
    }
}