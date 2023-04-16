package com.example.finaladminapp.navigation

import com.example.finaladminapp.screens.msgs.msgscreen

enum class ReaderScreens {
    SplashScreen,
    LoginScreen,
    CreateAccountScreen,
    ReaderHomeScreen,
    SearchScreen,
    DetailScreen,
    UpdateScreen,
    RealtimeScreen,
    postedscreen,
    msgscreen,
    ReaderStatsScreen;
    companion object {
        fun fromRoute(route: String): ReaderScreens
                = when(route?.substringBefore("/")){
            SplashScreen.name -> SplashScreen
            LoginScreen.name -> LoginScreen
            CreateAccountScreen.name -> CreateAccountScreen
            ReaderHomeScreen.name -> ReaderHomeScreen
            SearchScreen.name -> SearchScreen
            DetailScreen.name -> DetailScreen
            UpdateScreen.name -> UpdateScreen
            ReaderStatsScreen.name -> ReaderStatsScreen
            RealtimeScreen.name -> RealtimeScreen
            msgscreen.name -> msgscreen
            postedscreen.name -> postedscreen
            null -> ReaderHomeScreen
            else -> throw java.lang.IllegalArgumentException("Route $route is not recognised")

        }
    }
}