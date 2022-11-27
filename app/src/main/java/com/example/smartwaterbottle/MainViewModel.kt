package com.example.smartwaterbottle

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch


class MainViewModel : ViewModel(){
    private val _currentTemperature  = MutableStateFlow<String>("0")
    val currentTemp = _currentTemperature
    private val _currentWaterLeft  = MutableStateFlow<String>("0")
    val currentWaterLeft = _currentWaterLeft
    fun getCurrentTemperature(){
        viewModelScope.launch(Dispatchers.IO){
            val database : DatabaseReference = Firebase.database.reference

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

            database.child("temp").addValueEventListener(tempListener)


        }

    }

    fun getCurrentPercentage(){
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

            database.child("cm").addValueEventListener(cmListener)


        }

    }


}