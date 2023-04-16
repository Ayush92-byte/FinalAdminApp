package com.example.finaladminapp.screens.login

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavController
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.finaladminapp.R
import com.example.finaladminapp.components.EmailInput
import com.example.finaladminapp.components.LoginLogo
import com.example.finaladminapp.components.passwordInput
import com.example.finaladminapp.navigation.ReaderScreens

@Composable
fun ReaderLoginScreen(
    navController: NavController,
    viewModel: LoginScrenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val context = LocalContext.current
    val showLoginForm = rememberSaveable { mutableStateOf(true) }
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top) {
             LoginLogo()
            if(showLoginForm.value)
                UserForm(loading = false, isCreateAccount = false){ email, password ->
                    viewModel.signInWithEmailAndPassword(email, password,context){
                        navController.navigate(ReaderScreens.ReaderHomeScreen.name)
                    }
                }
            else{
                UserForm(loading = false, isCreateAccount = true){email, password ->
                    viewModel.createUserWithEmailAndPassword(email,password,context){
//                        navController.navigate(ReaderScreens.ReaderHomeScreen.name)
                    }

                }
            }

        }

        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier.padding(15.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val text = if(showLoginForm.value) "Sign up" else "Login"
            Text(text = "New User?")
            Text(text = text,
                modifier = Modifier
                    .clickable {
                        showLoginForm.value = !showLoginForm.value
                    }
                    .padding(10.dp),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.secondaryVariant)


        }

    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun UserForm(
    loading: Boolean = false,
    isCreateAccount: Boolean = false,
    onDone: (String, String) -> Unit = {email, pwd ->}
) {
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable{ mutableStateOf("") }
    val passwordVisibility = rememberSaveable{ mutableStateOf(false) }
    val passwordfocusRequest = FocusRequester.Default
    val keyboardcontroller = LocalSoftwareKeyboardController.current
    val valid = remember(email.value, password.value){
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }

    val modifier = Modifier
        .height(250.dp)
        .background(MaterialTheme.colors.background)
        .verticalScroll(rememberScrollState())
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        if(isCreateAccount) Text(text = stringResource(id = R.string.create_acct),
            modifier = Modifier.padding(4.dp)) else Text(text = "")
        EmailInput(emailState = email, enabled = !loading, onAction = KeyboardActions{
            passwordfocusRequest.requestFocus()
        })

        passwordInput(
            modifier = Modifier.focusRequester(passwordfocusRequest),
            passwordState = password,
            labelId = "Password",
            enabled = !loading, // todo changes
            passwordVisibility = passwordVisibility,
            onAction = KeyboardActions {
                if(!valid) return@KeyboardActions
                onDone(email.value.trim(), password.value.trim())
            })

        SubmitButton(
            textId = if(isCreateAccount)"Create Account" else "Login",
            loading = loading,
            validInputs = valid
        ){
            onDone(email.value.trim(), password.value.trim())
            keyboardcontroller?.hide()
        }
    }



}

@Composable
fun SubmitButton(textId: String,
                 loading: Boolean,
                 validInputs: Boolean,
                 onClick: () -> Unit) {
    Button(onClick,
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        enabled = !loading && validInputs,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF2C9695))
    ) {
        if(loading) CircularProgressIndicator(modifier = Modifier.size(25.dp))
        else Text(text = textId,modifier = Modifier.padding(5.dp))

    }

}