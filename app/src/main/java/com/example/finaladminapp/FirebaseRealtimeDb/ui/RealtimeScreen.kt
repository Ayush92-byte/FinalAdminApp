package com.example.adminapp.FirebaseRealtimeDb.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.adminapp.FirebaseRealtimeDb.RealtimeModelResponse
import com.example.adminapp.util.ResultState
import com.example.adminapp.util.showMsg
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun RealtimeScreen(
    isInsert: MutableState<Boolean> = mutableStateOf(false),
    viewModel: RealtimeViewModel = hiltViewModel(),
) {

    val title = remember { mutableStateOf("") }
    val des = remember { mutableStateOf("") }
    var skills = remember { mutableStateOf("") }
    val apply = remember { mutableStateOf("") }
    val opening = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val isDialog = remember { mutableStateOf(false) }
    val res = viewModel.res.value
    val isUpdate = remember { mutableStateOf(false) }



    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = title.value,
            onValueChange = { title.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            label = { Text(text = "Title") },
            shape = CutCornerShape(topStart = 15.dp, topEnd = 15.dp)
        )

        OutlinedTextField(
            value = des.value,
            onValueChange = { des.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            label = { Text(text = "description") },
            shape = CutCornerShape(topStart = 15.dp, topEnd = 15.dp)
        )

//        OutlinedTextField(value = skills.value,
//            onValueChange = {skills.value = it},
//            modifier = Modifier.fillMaxWidth().padding(18.dp),
//            label = {Text(text  = "skills required")},
//            shape = CutCornerShape(topStart = 15.dp, topEnd = 15.dp)
//        )
        var expanded by remember { mutableStateOf(false) }
        val list = listOf("Android Development", "Game Development", "Machine Learning", "Artificial Intelligence",
            "Web Development","Cyber Security","Data Science")
//

        var textFieldSize by remember { mutableStateOf(Size.Zero) }

        val icon = if (expanded) {
            Icons.Filled.KeyboardArrowUp
        } else {
            Icons.Filled.KeyboardArrowDown
        }
        Column(modifier = Modifier.padding(20.dp)) {

            OutlinedTextField(
                value = skills.value,
                onValueChange = { skills.value= it },
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        textFieldSize = coordinates.size.toSize()
                    },
                label = { Text(text = "Skills Required") },
                trailingIcon = {
                    Icon(icon, "", Modifier.clickable { expanded = !expanded })
                }
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
            ) {

                list.forEach { label ->
                    DropdownMenuItem(onClick = {
                        skills.value = label
                        expanded = false
                    }) {
                        Text(text = label)

                    }
                }

            }
        }


        OutlinedTextField(
            value = apply.value,
            onValueChange = { apply.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            label = { Text(text = "who can apply") },
            shape = CutCornerShape(topStart = 15.dp, topEnd = 15.dp)
        )

        OutlinedTextField(
            value = opening.value,
            onValueChange = { opening.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            label = { Text(text = "number of opening") },
            shape = CutCornerShape(topStart = 15.dp, topEnd = 15.dp)
        )
        Button(onClick = {
            scope.launch(Dispatchers.Main) {
                viewModel.insert(
                    RealtimeModelResponse.RealtimeItems(
                        title.value,
                        des.value,
                        skills.value,
                        apply.value,
                        opening.value
                    )
                ).collect {
                    when (it) {
                        is ResultState.Success -> {
                            context.showMsg(
                                msg = it.data
                            )
                            title.value = ""
                            des.value = ""
                            skills.value = ""
                            apply.value = ""
                            opening.value = ""
                        }
                        is ResultState.Failure -> {
                            context.showMsg(
                                msg = it.msg.toString()
                            )

                        }
                        ResultState.Loading -> {
                            Log.d("TAGGG", "ANish K")
                        }
                    }
                }
            }
        }) {
            Text(text = "Save")

        }
    }


//    if(isInsert.value){
//        AlertDialog(onDismissRequest = { isInsert.value = false },
//        text = {
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(10.dp),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                TextField(value = title.value, onValueChange = {
//                    title.value=it
//                },
//                placeholder = { Text(text = "Title")})
//
//                TextField(value = des.value, onValueChange = {
//                    des.value=it
//                },
//                    placeholder = { Text(text = "description")})
//
//                TextField(value = skills.value, onValueChange = {
//                    skills.value=it
//                },
//                    placeholder = { Text(text = "Skills Required")})
//
//                TextField(value = apply.value, onValueChange = {
//                    apply.value=it
//                },
//                    placeholder = { Text(text = "Who Can Apply")})
//
//                TextField(value = opening.value, onValueChange = {
//                    opening.value=it
//                },
//                    placeholder = { Text(text = "Number Of Opening")})
//            }
//        },
//            buttons = {
//                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
//                    Button(onClick = {
//                        scope.launch(Dispatchers.Main){
//                            viewModel.insert(
//                                RealtimeModelResponse.RealtimeItems(
//                                    title.value,
//                                    des.value,
//                                    skills.value,
//                                    apply.value,
//                                    opening.value
//                                )
//                            ).collect{
//                                when(it){
//                                    is ResultState.Success->{
//                                        context.showMsg(
//                                            msg = it.data
//                                        )
//                                        isDialog.value = false
//                                        isInsert.value = false
//                                    }
//                                    is ResultState.Failure->{
//                                        context.showMsg(
//                                            msg = it.msg.toString()
//                                        )
//                                        isDialog.value = false
//                                    }
//                                    ResultState.Loading->{
//                                        isDialog.value = true
//                                    }
//                                }
//                            }
//                        }
//                    }) {
//                        Text(text = "Save")
//
//                    }
//                }
//            }
//
//            )
//    }

//    if(isUpdate.value){
//        com.example.adminapp.FirebaseRealtimeDb.ui.Update(
//            isUpdate = isUpdate,
//            itemState = viewModel.updateRes.value,
//            viewModel =viewModel
//        )
//    }
//
//    if(res.item.isNotEmpty()){
//
//        LazyColumn{
//            items(
//                res.item,
//                key = {
//                    it.key!!
//                }
//            ){res->
//                EachRow(itemState = res.item!!,
//                onUpdate = {
//                    isUpdate.value = true
//                    viewModel.setData(res)
//                }){
//                    scope.launch (Dispatchers.Main){
//                        viewModel.delete(res.key!!).collect{
//                            when(it){
//                                is ResultState.Success->{
//                                    context.showMsg(
//                                        msg = it.data
//                                    )
//                                    isDialog.value = false
//                                }
//                                is ResultState.Failure->{
//                                    context.showMsg(
//                                        msg = it.msg.toString()
//                                    )
//                                    isDialog.value = false
//                                }
//                                ResultState.Loading->{
//                                    isDialog.value = true
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    if(res.isLoading){
//        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Center){
//            CircularProgressIndicator()
//        }
//    }
//
//    if(res.error.isNotEmpty()){
//        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Center){
//            Text(res.error)
//        }
//    }
}

@Composable
fun EachRow(
    itemState: RealtimeModelResponse.RealtimeItems,
    onUpdate: () -> Unit = {},
    onDelete: () -> Unit = {}
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 1.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onUpdate()
            }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = itemState.title!!,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    )
                    IconButton(
                        onClick = {
                            onDelete()
                        },
                        modifier = Modifier.align(CenterVertically)
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = "", tint = Color.Red)
                    }
                }
                Text(
                    text = itemState.description!!,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Gray
                    )
                )
            }
        }
    }
}

@Composable
fun Update(
    isUpdate: MutableState<Boolean>,
    itemState: RealtimeModelResponse,
    viewModel: RealtimeViewModel
) {

    val title = remember { mutableStateOf(itemState.item?.title) }
    val des = remember { mutableStateOf(itemState.item?.description) }
    val skills = remember { mutableStateOf(itemState.item?.skills) }
    val apply = remember { mutableStateOf(itemState.item?.whocanapply) }
    val opening = remember { mutableStateOf(itemState.item?.noofopening) }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current



    if (isUpdate.value) {
        AlertDialog(onDismissRequest = { isUpdate.value = false },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(value = title.value!!, onValueChange = {
                        title.value = it
                    },
                        placeholder = { Text(text = "Title") })

                    TextField(value = des.value!!, onValueChange = {
                        des.value = it
                    },
                        placeholder = { Text(text = "description") })

                    TextField(value = skills.value!!, onValueChange = {
                        skills.value = it
                    },
                        placeholder = { Text(text = "Skills Required") })

                    TextField(value = apply.value!!, onValueChange = {
                        apply.value = it
                    },
                        placeholder = { Text(text = "Who Can Apply") })

                    TextField(value = opening.value!!, onValueChange = {
                        opening.value = it
                    },
                        placeholder = { Text(text = "Number Of Opening") })


                }
            },
            buttons = {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Button(onClick = {
                        scope.launch(Dispatchers.Main) {
                            viewModel.update(
                                RealtimeModelResponse(
                                    item = RealtimeModelResponse.RealtimeItems(
                                        title.value,
                                        des.value,
                                        skills.value,
                                        apply.value,
                                        opening.value
                                    ),
                                    key = itemState.key
                                )
                            ).collect {
                                when (it) {
                                    is ResultState.Success -> {
                                        context.showMsg(
                                            msg = it.data
                                        )
                                        isUpdate.value = false

                                    }
                                    is ResultState.Failure -> {
                                        context.showMsg(
                                            msg = it.msg.toString()
                                        )
                                    }
                                    ResultState.Loading -> {
                                    }
                                }
                            }
                        }
                    }) {
                        Text(text = "Save")
                    }


                }
            }

        )

    }
} 