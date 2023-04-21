package com.example.firelogin2.navigation

sealed class Screens(val route:String){
    object SignInScreen:Screens(route = "sign_in_screen")
    object SignUpScreen:Screens(route = "sign_up_screen")
    object WelcomeScreen:Screens(route = "welcome_screen")
    object AddEditDataScreen:Screens(route = "add_edit_data_screen")
    object ReadDataScreen:Screens(route = "read_data_screen")
}