package com.example.finaladminapp.screens.msgs

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.adminapp.FirebaseRealtimeDb.ui.RealtimeViewModel
import com.example.finaladminapp.FirebaseRealtimeDb.ui.BuildText
import com.example.finaladminapp.R
import com.example.finaladminapp.navigation.ReaderScreens

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun msgscreen(navController: NavController,
    getmsgs : MutableState<Boolean> = mutableStateOf(false),
    viewModel: RealtimeViewModel = hiltViewModel()
) {
    val list = viewModel._msg.value.item

    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                actions = {
                    IconButton(onClick = {navController.navigate(ReaderScreens.ReaderHomeScreen.name)}) {
                        Icon(painter = painterResource(id = R.drawable.baseline_arrow_back_ios_24),
                            contentDescription = "BAck")
                    }
                }
                ,
                backgroundColor = Color(0xFF2C9695)
            )
        }
    ){
        LazyColumn{
            items(items = list){

                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                    backgroundColor = Color(0xFFECEAB0),
                    elevation = 10.dp) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 18.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        BuildText(title = "Project Name", description =it.item?.projectName.toString())
                        BuildText(title = "Name", description =it.item?.name.toString())
                        BuildText(title = "Batch", description =it.item?.batch.toString())
                        BuildText(title = "Roll Number", description =it.item?.rollno.toString())
                        BuildText(title = "Message", description =it.item?.message.toString())
                    }

                }
            }
        }
    }



}

@Composable
fun BuildText(title : String,
              description : String){
    Text(buildAnnotatedString {
        withStyle(style = SpanStyle(
            color = Color.DarkGray,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        ){
            append("$title: ")
        }
        withStyle(style = SpanStyle(
            color = Color.DarkGray,
            fontSize = 18.sp,
            fontWeight = FontWeight.Light
        )
        ){
            append(description)
        }


    }, modifier = Modifier.padding(10.dp))
}