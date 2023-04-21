package com.example.firelogin2.screens.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.firelogin2.data.MoneyData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CRUDViewModel :ViewModel(){
    fun saveData(moneyData: MoneyData,context: Context) = CoroutineScope(Dispatchers.IO).launch{
        val fireStoreRef = Firebase.firestore.collection("user").document(moneyData.uid)

        try {
            fireStoreRef.set(moneyData).addOnSuccessListener {
                Toast.makeText(context,"Saved successfully", Toast.LENGTH_SHORT).show()
            }
        }catch (e:Exception){
            Toast.makeText(context,"${e.message}", Toast.LENGTH_SHORT).show()
        }

    }

    fun retrieveData(
        uId:String,
        context: Context,
        data: (MoneyData) -> Unit
    ) = CoroutineScope(Dispatchers.IO).launch{
        val fireStoreRef = Firebase.firestore.collection("user")
            .document(uId)

        try {
            fireStoreRef.get().addOnSuccessListener {
                if (it.exists()){
                    val userData = it.toObject<MoneyData>()!!
                    data(userData)
                }else{
                    Toast.makeText(context,"User not found",Toast.LENGTH_SHORT).show()
                }
            }
        }catch (e:Exception){
            Toast.makeText(context,"${e.message}",Toast.LENGTH_SHORT).show()
        }
    }

    fun deleteData(
        uId:String,
        context: Context,
        navController: NavController
    ) = CoroutineScope(Dispatchers.IO).launch{
        val fireStoreRef = Firebase.firestore.collection("user")
            .document(uId)

        try {
            fireStoreRef.delete().addOnSuccessListener {
                Toast.makeText(context,"Deleted successfully",Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            }
        }catch (e:Exception){
            Toast.makeText(context,"${e.message}",Toast.LENGTH_SHORT).show()
        }
    }
}