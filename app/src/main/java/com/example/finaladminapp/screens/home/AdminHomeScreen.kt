package com.example.finaladminapp.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.finaladminapp.R
import com.example.finaladminapp.navigation.ReaderScreens
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Home(navController: NavController) {
    val auth = FirebaseAuth.getInstance()

    val context = LocalContext.current

    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                actions = {
                    IconButton(onClick = {
                        FirebaseAuth.getInstance().signOut().run{
                            navController.navigate(ReaderScreens.LoginScreen.name)
                        }
                         }) {
                        Icon(painter = painterResource(id = R.drawable.baseline_logout_24),
                            contentDescription = "LogOut")
                    }
                }
                ,
                backgroundColor = Color(0xFF2C9695)
            )
        }
            ){

        Column(modifier = Modifier.padding(16.dp)) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable(
                        interactionSource = CreateMutableIteractionSource(),
                        indication = CreateIndication(color = Color.Red),
                        onClick = {
                            navController.navigate(ReaderScreens.RealtimeScreen.name)
                        }
                    ),
                backgroundColor = Color(0xFFBE96EF),
                elevation = 8.dp,
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "Post Projects",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.SemiBold
                )

            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable(
                        interactionSource = CreateMutableIteractionSource(),
                        indication = CreateIndication(color = Color.Red),
                        onClick = {
                            navController.navigate(ReaderScreens.postedscreen.name)
                        }
                    ),
                backgroundColor = Color(0xFFED6DCA),
                elevation = 8.dp,
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "Posted Projects",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.SemiBold
                )

            }


            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable(
                        interactionSource = CreateMutableIteractionSource(),
                        indication = CreateIndication(color = Color.Red),
                        onClick = {
                            navController.navigate(ReaderScreens.msgscreen.name)
                        }
                    ),
                backgroundColor = Color(0xFFBE96EF),
                elevation = 8.dp,
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "Student messages",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.SemiBold
                )

            }
        }
    }

}
@Composable
fun CreateMutableIteractionSource(): MutableInteractionSource = remember {
    MutableInteractionSource()
}

@Composable
fun CreateIndication(bounded: Boolean = true, color: Color) =
    rememberRipple(bounded = bounded,color = color)
