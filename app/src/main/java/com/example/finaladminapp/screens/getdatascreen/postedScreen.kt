package com.example.finaladminapp.FirebaseRealtimeDb.ui

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.example.adminapp.util.ResultState
import com.example.adminapp.util.showMsg
import com.example.finaladminapp.R
import com.example.finaladminapp.navigation.ReaderScreens
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun postedscreen(navController: NavController,
    isget : MutableState<Boolean> = mutableStateOf(false),
    viewModel: RealtimeViewModel = hiltViewModel()

) {

    val listBigData = viewModel.res.value.item
    val listData = listBigData.filter {
        it.item?.nameofteacher == "Anish Kumar"
    }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current


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
            items(items = listData){

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
                        BuildText(title = "Project Title", description =it.item?.title.toString())
                        BuildText(title = "Description", description =it.item?.description.toString())
                        BuildText(title = "SKills Required", description =it.item?.skills.toString())
                        BuildText(title = "Who Can Apply", description =it.item?.whocanapply.toString())
                        BuildText(title = "Number Of Opening", description =it.item?.noofopening.toString())

                        Button(onClick = {
                            scope.launch (Dispatchers.Main){
                                viewModel.delete(it.key!!).collect{
                                    when(it){
                                        is ResultState.Success->{
                                            context.showMsg(
                                                msg = it.data
                                            )
                                        }
                                        is ResultState.Failure->{
                                            context.showMsg(
                                                msg = it.msg.toString()
                                            )

                                        }
                                        ResultState.Loading->{
                                            Log.d("TAGG","    ")
                                        }
                                    }
                                }
                            }
                        }) {
                            Text(text = "Delete")
                        }
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