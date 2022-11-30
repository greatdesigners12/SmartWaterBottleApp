package com.example.smartwaterbottle

import android.content.ContentValues
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow

class LoginViewModel : ViewModel() {
    val loginStatus = MutableStateFlow<String>("")


    fun checkIfNodeExist(node : String){
        val nodeListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if (dataSnapshot.getValue() == null) {
                    loginStatus.value = "User $node Not Found"

                }else{
                    loginStatus.value = "Success"
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        val database : DatabaseReference = Firebase.database.reference
        database.child(node).addListenerForSingleValueEvent(nodeListener)
    }


}