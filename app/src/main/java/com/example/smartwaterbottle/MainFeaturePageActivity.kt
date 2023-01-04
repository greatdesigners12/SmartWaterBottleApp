package com.example.smartwaterbottle

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.smartwaterbottle.Navigation.NavigationEnum
import com.example.smartwaterbottle.helper.CustomCircularProgressIndicator
import com.example.smartwaterbottle.helper.customProgressBar

@Composable
fun MainScreen(navController: NavController ,viewModel: MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {

    val currentTemp = remember{
        mutableStateOf(50f)
    }

    val currentPercentage = remember{
        mutableStateOf(20f)
    }

    val currbuzstate = remember{
        mutableStateOf("")
    }

    val context = LocalContext.current
    val dataStore =  StoreUserId(context)

    LaunchedEffect(key1 = viewModel.buzstate.collectAsState(initial = "0").value){
        viewModel.getBuzzerSetting(dataStore.getUserId)
        currbuzstate.value = viewModel.buzstate.value
    }

    LaunchedEffect(key1 = viewModel.logoutState.collectAsState(initial = "").value){
        if(viewModel.logoutState.value){
            navController.navigate(NavigationEnum.LoginScreenActivity.name)
        }
    }

    LaunchedEffect(key1 = viewModel.currentTemp.collectAsState(initial = "0").value){
        viewModel.getCurrentTemperature(dataStore.getUserId)
        if(viewModel.currentTemp.value != "null"){
            Log.d(TAG, "MainScreen: ${viewModel.currentTemp.value}")
            currentTemp.value = viewModel.currentTemp.value.toFloat()
            Log.d(ContentValues.TAG, "MainScreen: ${currentTemp.value}")
        }

    }

    LaunchedEffect(key1 = viewModel.currentWaterLeft.collectAsState(initial = "0").value){
        viewModel.getCurrentPercentage(dataStore.getUserId)
        if(viewModel.currentTemp.value != "null"){
            if(viewModel.currentWaterLeft.value.toFloat() < 0 || viewModel.currentWaterLeft.value.toFloat() > 20){
                currentPercentage.value = -1f
            }else{
                currentPercentage.value = 100 - (viewModel.currentWaterLeft.value.toFloat() - 2) / 18 * 100
                Log.d(ContentValues.TAG, "MainScreen: ${currentPercentage.value}")
            }
        }

    }



    Column(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally){
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End){
            Image(painter = painterResource(id = R.drawable.ic_baseline_logout_24), modifier = Modifier.clickable {
                 viewModel.logout(dataStore)
            } , contentDescription = "Logout")
        }
        CustomCircularProgressIndicator(
            modifier = Modifier
                .size(260.dp)
                .background(Color.White)
            ,
            positionValue = currentTemp,
            primaryColor = Color.Cyan,
            secondaryColor = Color.White,
            circleRadius = 230f,
            onPositionChange = {

            }
        )
        LinearProgressIndicator(progress = 0.5f)
        Spacer(modifier = Modifier.height(20.dp))
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("SISA AIR SAAT INI", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(5.dp))
            customProgressBar(currentPercentage)
        }
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Buzzer Settings",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        var selectedValue = remember { mutableStateOf(currbuzstate) }
        val isSelectedItem: (String) -> Boolean = { selectedValue.value.value == it }
        val onChangeState: (String) -> Unit = { selectedValue.value.value = it }

        val items = listOf("On", "Off")
        Column(Modifier.padding(8.dp)) {
            items.forEach { item ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .selectable(
                            selected = isSelectedItem(item),
                            onClick = {
                                onChangeState(item)
                                viewModel.changeBuzzerSetting(selectedValue.value, dataStore.getUserId)
                            },
                            role = Role.RadioButton,
                        )
                        .padding(8.dp)
                ) {
                    RadioButton(
                        selected = isSelectedItem(item),
                        onClick = null
                    )
                    Text(
                        text = item,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}