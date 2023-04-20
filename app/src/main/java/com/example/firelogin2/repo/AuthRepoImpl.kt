package com.example.firelogin2.repo

import com.example.firelogin2.utils.ResultState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class AuthRepoImpl @Inject constructor(
private val auth: FirebaseAuth,
private val firebaseAuth: FirebaseAuth
):AuthRepo{


    override fun createUser(authUser: AuthUser): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)
        firebaseAuth.createUserWithEmailAndPassword(authUser.email, authUser.password)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    trySend(ResultState.Success("Signed up successfully"))
                }
            }.addOnFailureListener{
                trySend(ResultState.Failure(it))
            }
        awaitClose {
            close()
        }
    }

    override fun loginUser(authUser: AuthUser): Flow<ResultState<String>>  = callbackFlow{
        trySend(ResultState.Loading)
        firebaseAuth.signInWithEmailAndPassword(authUser.email, authUser.password)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    trySend(ResultState.Success("Signed in successfully"))
                }
            }.addOnFailureListener{
                trySend(ResultState.Failure(it))
            }
        awaitClose {
            close()
        }
    }
}