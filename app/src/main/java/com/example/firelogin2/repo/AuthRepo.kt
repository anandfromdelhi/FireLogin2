package com.example.firelogin2.repo

import com.example.firelogin2.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface AuthRepo {
    fun createUser(authUser: AuthUser): Flow<ResultState<String>>
    fun loginUser(authUser: AuthUser): Flow<ResultState<String>>
}