package com.example.smartwaterbottle

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class MainViewModel : ViewModel(){

    private val _currentTemperature  = MutableStateFlow<String>("0")
    val currentTemp = _currentTemperature
    private val _logout  = MutableStateFlow(false)
    val logoutState = _logout
    fun getCurrentTemperature(userId : Flow<String?>){
        viewModelScope.launch(Dispatchers.IO){
            val database : DatabaseReference = Firebase.database.reference
            Log.d(TAG, "getCurrentTemperature: hello world")
            val tempListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    _currentTemperature.value = dataSnapshot.value.toString()


                    Log.d(TAG, "onDataChange: ${dataSnapshot.value}")
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Getting Post failed, log a message
                    Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                }
            }

            userId.collect{
                if (it != null) {
                    database.child(it).child("temp").addValueEventListener(tempListener)
                }
            }




        }

    }

    private val _currentWaterLeft  = MutableStateFlow<String>("0")
    val currentWaterLeft = _currentWaterLeft

    fun getCurrentPercentage(userId : Flow<String?>){
        viewModelScope.launch(Dispatchers.IO){
            val database : DatabaseReference = Firebase.database.reference

            val cmListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    _currentWaterLeft.value = dataSnapshot.value.toString()
                    Log.d(TAG, "onDataChange cm: ${_currentWaterLeft.value}")

                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Getting Post failed, log a message
                    Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                }
            }
            userId.collect{
                
                if (it != null) {
                    Log.d(TAG, "getCurrentPercentage: ${it}")
                    database.child(it).child("cm").addValueEventListener(cmListener)
                }
            }



        }

    }


    private var _buzstate  = MutableStateFlow<String>("0")
    val buzstate = _buzstate
    fun getBuzzerSetting(userId : Flow<String?>) {
        viewModelScope.launch(Dispatchers.IO) {
            val database : DatabaseReference = Firebase.database.reference
            userId.collect{
                if (it != null) {
                    database.child(it).child("buzzer").get().addOnSuccessListener {
                        _buzstate.value = it.value.toString()
                    }
                }
            }
        }



    }


    fun changeBuzzerSetting(value: MutableState<String>, userId : Flow<String?>){
        viewModelScope.launch(Dispatchers.IO) {
            val database : DatabaseReference = Firebase.database.reference
            userId.collect{
                if (it != null) {
                    database.child(it).child("buzzer").setValue(value.value)
                };

            }
        }



    }

    fun logout( storeUserId: StoreUserId){
        viewModelScope.launch(Dispatchers.IO){
            storeUserId.deleteUserId()
            storeUserId.getUserId.collect{
                if(it == ""){
                    _logout.value = true
                }
            }

        }
    }


}