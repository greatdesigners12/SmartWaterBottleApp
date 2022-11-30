package com.example.smartwaterbottle.helper

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SimpleAlertDialog(title : String, message : String, onDismissRequest : () -> Unit, onAction : () -> Unit) {
    val isSuccess = title == "success"
    AlertDialog(title={ Text(title) }, text = { Text(message) }, buttons = {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(onClick = onAction) {
                Text(if(isSuccess) "Ok" else "Try again")
            }
        }

    }, onDismissRequest = {onDismissRequest()})
}