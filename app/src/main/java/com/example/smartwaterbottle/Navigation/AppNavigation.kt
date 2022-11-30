package com.example.smartwaterbottle.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smartwaterbottle.LoginActivity
import com.example.smartwaterbottle.MainActivity
import com.example.smartwaterbottle.MainScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = NavigationEnum.LoginScreenActivity.name){
        composable(NavigationEnum.LoginScreenActivity.name){
            LoginActivity(navController)
        }

        composable(NavigationEnum.MainScreenActivity.name){
            MainScreen(navController)
        }
    }
}