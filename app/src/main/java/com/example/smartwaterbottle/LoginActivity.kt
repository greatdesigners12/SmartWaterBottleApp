package com.example.smartwaterbottle

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.smartwaterbottle.Navigation.NavigationEnum
import com.example.smartwaterbottle.helper.SimpleAlertDialog
import com.example.smartwaterbottle.helper.basicInputField
import kotlinx.coroutines.flow.collect


@Composable
fun LoginActivity(navController: NavController, viewModel : LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {

    val inputValue = remember{
        mutableStateOf("")
    }

    val context = LocalContext.current
    val dataStore =  StoreUserId(context)

    val showAlertDialog = remember{
        mutableStateOf(false)
    }

    val alertDialogMsg = remember{
        mutableStateOf("")
    }
    LaunchedEffect(key1 = true){
        dataStore.getUserId.collect{
            if(it != ""){
                navController.popBackStack()
                navController.navigate(NavigationEnum.MainScreenActivity.name)
            }
        }
    }



    LaunchedEffect(key1 = viewModel.loginStatus.collectAsState(initial = "").value){
        if(viewModel.loginStatus.value != ""){
            if(viewModel.loginStatus.value == "Success"){
                dataStore.setUserId(viewModel.loginStatus.value)
                navController.popBackStack()
                navController.navigate(NavigationEnum.MainScreenActivity.name)
            }else{
                alertDialogMsg.value = viewModel.loginStatus.value
                showAlertDialog.value = true
            }
        }

    }



    val closeAlertDialog : () -> Unit = {
        alertDialogMsg.value = ""
        showAlertDialog.value = false
    }

    if(showAlertDialog.value){
        SimpleAlertDialog(title = "Error", message = alertDialogMsg.value, onDismissRequest = {
            closeAlertDialog()

        }) {
            closeAlertDialog()
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
        Text("Welcome", fontWeight = FontWeight.Bold, fontSize = 30.sp)
        Spacer(modifier = Modifier.height(10.dp))
        basicInputField(label = "Authentication Code", inputValue = inputValue.value){
            inputValue.value = it
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { viewModel.checkIfNodeExist(inputValue.value) }) {
            Text("LOGIN")
        }
    }
}