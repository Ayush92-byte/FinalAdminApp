package com.example.finaladminapp.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.finaladminapp.R
import com.example.finaladminapp.navigation.ReaderScreens
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Preview
@Composable
fun SplashScreen(navController: NavController = NavController(context = LocalContext.current)
                 ) {

    var scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }
   
    LaunchedEffect(key1 = true, block = {
        scale.animateTo(0.9f,
        animationSpec = tween(
            durationMillis = 800,
            easing = {
                OvershootInterpolator(4f)
                    .getInterpolation(it)
            }
        ))
        delay(2000)
        
        if(FirebaseAuth.getInstance().currentUser?.email?.isNotEmpty() == true)
        {
            navController.navigate(ReaderScreens.ReaderHomeScreen.name)
        }
        else
        {
            navController.navigate(ReaderScreens.LoginScreen.name)
        }
    })
    
    Surface(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.scale(scale.value)){
           Image(painter = painterResource(id = R.drawable.iiitrlogo)
               , contentDescription = "Splash Screen",
           modifier = Modifier.size(300.dp))
        }
        
    }

}


//if (FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()) {
//    navController.navigate(ReaderScreens.LoginScreen.name)
//} else {
//    navController.navigate(ReaderScreens.ReaderHomeScreen.name)
//}