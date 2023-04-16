package com.example.finaladminapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.adminapp.FirebaseRealtimeDb.ui.RealtimeScreen
import com.example.finaladminapp.FirebaseRealtimeDb.ui.postedscreen
import com.example.finaladminapp.screens.SplashScreen
import com.example.finaladminapp.screens.home.Home
import com.example.finaladminapp.screens.information.ReaderStatsScreen
import com.example.finaladminapp.screens.login.ReaderLoginScreen
import com.example.finaladminapp.screens.msgs.msgscreen
import com.google.firebase.auth.FirebaseAuth


@Composable
fun ReaderNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = ReaderScreens.SplashScreen.name ){
        composable(ReaderScreens.SplashScreen.name){


            SplashScreen(navController = navController)
        }

        composable(ReaderScreens.ReaderHomeScreen.name){
            if (FirebaseAuth.getInstance().currentUser != null){
                Home(navController = navController)
            }else
            {
                navController.navigate(ReaderScreens.LoginScreen.name){
                    popUpTo(ReaderScreens.ReaderHomeScreen.name){
                        inclusive = true
                    }
                }

            }

        }

        composable(ReaderScreens.LoginScreen.name){
            ReaderLoginScreen(navController = navController)
        }
        composable(ReaderScreens.ReaderStatsScreen.name){
            ReaderStatsScreen(navController = navController)
        }
        composable(ReaderScreens.RealtimeScreen.name){
            RealtimeScreen()
        }

        composable(ReaderScreens.postedscreen.name){
            postedscreen(navController = navController)
        }

        composable(ReaderScreens.msgscreen.name){
            msgscreen(navController = navController)
        }
    }
}