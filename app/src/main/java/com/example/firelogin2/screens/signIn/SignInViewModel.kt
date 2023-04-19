package com.example.firelogin2.screens.signIn

import androidx.lifecycle.ViewModel
import com.example.firelogin2.data.SignInResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignInViewModel() : ViewModel() {
    private val _state = MutableStateFlow(SignInState())
    val signInState = _state.asStateFlow()

    fun signInResult(result: SignInResult) {
        _state.update {
            it.copy(
                isSignInSuccessful = result.data != null,
                signInError = result.errorMessage
            )
        }
    }

    fun resetState() {
        _state.update {
            SignInState()
        }
    }
}