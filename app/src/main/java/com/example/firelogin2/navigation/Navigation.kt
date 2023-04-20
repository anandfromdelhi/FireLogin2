package com.example.firelogin2.navigation

import android.content.Context
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.firelogin2.auth.GoogleAuthClient
import com.example.firelogin2.screens.SignInScreen
import com.example.firelogin2.screens.SignUpScreen
import com.example.firelogin2.screens.WelcomeScreen
import com.example.firelogin2.screens.signIn.SignInViewModel
import kotlinx.coroutines.launch

@Composable
fun Navigation(googleAuthUiClient: GoogleAuthClient, applicationContext: Context) {
    val scope = rememberCoroutineScope()

    val navController: NavHostController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.SignInScreen.route) {

        //sign in screen
        composable(route = Screens.SignInScreen.route) {
            val viewModel = viewModel<SignInViewModel>()
            val state by viewModel.signInState.collectAsState()


            LaunchedEffect(key1 = Unit) {
                if (googleAuthUiClient.getSignedInUser() != null) {
                    navController.navigate(Screens.WelcomeScreen.route)
                }
            }

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartIntentSenderForResult(),
                onResult = { result ->
                    if (result.resultCode == ComponentActivity.RESULT_OK) {

                        scope.launch {
                            val signInResult = googleAuthUiClient.signInWithIntent(
                                intent = result.data ?: return@launch
                            )
                            viewModel.signInResult(signInResult)
                        }
                    }

                }
            )
            LaunchedEffect(key1 = state.isSignInSuccessful) {
                if (state.isSignInSuccessful) {
                    Toast.makeText(
                        applicationContext,
                        "Sign in successful",
                        Toast.LENGTH_LONG
                    ).show()
                    navController.navigate(Screens.WelcomeScreen.route)
                    viewModel.resetState()
                }
            }
            SignInScreen(signInState = state, onSignInClick = {
                scope.launch {
                    val signInIntentSender = googleAuthUiClient.signIn()
                    launcher.launch(
                        IntentSenderRequest.Builder(
                            signInIntentSender ?: return@launch
                        ).build()
                    )
                }
            }, navController = navController)
        }

        //sign up screen
        composable(route = Screens.SignUpScreen.route) {
            SignUpScreen(navController = navController)
        }

        //welcome screen
        composable(route = Screens.WelcomeScreen.route) {
            WelcomeScreen(
                userData = googleAuthUiClient.getSignedInUser(),
                onSignOut = {
                    scope.launch {
                        googleAuthUiClient.signOut()
                        Toast.makeText(applicationContext, "Signed out", Toast.LENGTH_LONG)
                            .show()
                        navController.popBackStack()
                    }
                }
            )
        }
    }

}
