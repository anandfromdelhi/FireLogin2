package com.example.firelogin2.screens.viewmodels

import androidx.lifecycle.ViewModel
import com.example.firelogin2.repo.AuthRepo
import com.example.firelogin2.data.AuthUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repo: AuthRepo
) : ViewModel() {
    fun createUser(authUser: AuthUser) = repo.createUser(authUser)
    fun loginUser(authUser: AuthUser) = repo.loginUser(authUser)

}