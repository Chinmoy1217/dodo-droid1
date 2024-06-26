package com.example.myapplication.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.screen.AddDataScreen
import com.example.myapplication.screen.GetDataScreen
import com.example.myapplication.screen.MainScreen
import com.example.myapplication.util.SharedViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    sharedViewModel: SharedViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screens.MainScreen.route
    ) {
        // main screen
        composable(
            route = Screens.MainScreen.route
        ) {
            MainScreen(
                navController = navController,
            )
        }
        // get data screen
        composable(
            route = Screens.GetDataScreen.route
        ) {
            GetDataScreen(
                navController = navController,
                sharedViewModel = sharedViewModel
            )
        }
        // add data screen
        composable(
            route = Screens.AddDataScreen.route
        ) {
            AddDataScreen(
                navController = navController,
                sharedViewModel = sharedViewModel
            )
        }
    }
}