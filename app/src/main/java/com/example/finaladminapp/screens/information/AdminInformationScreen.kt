package com.example.finaladminapp.screens.information

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import com.example.finaladminapp.R
import com.example.finaladminapp.navigation.ReaderScreens


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ReaderStatsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                actions = {

                    Icon(
                        painter = painterResource(id = R.drawable.baseline_arrow_back_ios_24),
                        contentDescription = "Back"
                    )

                },
                backgroundColor = Color(0xFF2C9695)
            )
        }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            var text by remember { mutableStateOf("") }
            var des by remember { mutableStateOf("") }
            var skills by remember { mutableStateOf("") }
            var whocanapply by remember { mutableStateOf("") }
            var noofopening by remember { mutableStateOf("") }
            var nameofteacher by remember { mutableStateOf("") }

            val context = LocalContext.current

            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Title") },
                shape = CutCornerShape(topStart = 15.dp, topEnd = 15.dp)
            )


            OutlinedTextField(
                value = des,
                onValueChange = { des = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Description") },
                shape = CutCornerShape(topStart = 15.dp, topEnd = 15.dp)
            )

//            @Composable
//            fun dropdownMenu(){
            var expanded by remember { mutableStateOf(false) }
            val list = listOf("kotlin", "java", "dart", "python")
//                var skills by remember { mutableStateOf("") }

            var textFieldSize by remember { mutableStateOf(Size.Zero) }

            val icon = if (expanded) {
                Icons.Filled.KeyboardArrowUp
            } else {
                Icons.Filled.KeyboardArrowDown
            }
            Column(modifier = Modifier.padding(20.dp)) {

                OutlinedTextField(
                    value = skills,
                    onValueChange = { skills = it },
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
                            skills = label
                            expanded = false
                        }) {
                            Text(text = label)

                        }
                    }

                }
            }


//            }


            OutlinedTextField(
                value = whocanapply,
                onValueChange = {
                    whocanapply = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Who Can Apply") },
                shape = CutCornerShape(topStart = 15.dp, topEnd = 15.dp)
            )


            OutlinedTextField(
                value = noofopening,
                onValueChange = {
                    noofopening = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Number Of Opening") },
                shape = CutCornerShape(topStart = 15.dp, topEnd = 15.dp)
            )




            Button(
                onClick = {
                    navController.navigate(ReaderScreens.RealtimeScreen.name)
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, Color.Black),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save")
            }
        }
    }


}

