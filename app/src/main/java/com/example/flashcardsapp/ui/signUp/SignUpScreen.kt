package com.example.flashcardsapp.ui.signUp

import android.content.Context.MODE_PRIVATE
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flashcardsapp.ui.theme.buttonDarkBrown
import com.example.flashcardsapp.ui.theme.buttonLightBrown
import kotlinx.coroutines.launch

const val SHARED_PREFS = "sharedPrefs"

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel, onSignUp: () -> Unit = { }, goToSignIn: () -> Unit = { }
) {
    var email by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }


    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val state = viewModel.signUpState.collectAsState(initial = null)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 30.dp, end = 30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Register", fontWeight = FontWeight.Bold, fontSize = 30.sp, color = Color.Black
        )

        Spacer(modifier = Modifier.height(100.dp))

        Text(
            text = "Enter you credentials to register",
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            color = Color.Gray
        )
        TextField(value = email, onValueChange = {
            email = it
        }, modifier = Modifier.fillMaxWidth(), colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            cursorColor = Color.Black,
            disabledLabelColor = Color.Gray,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            textColor = Color.Black
        ), shape = RoundedCornerShape(8.dp), singleLine = true, placeholder = {
            Text(text = "Email", color = Color.Gray)
        })
        Spacer(modifier = Modifier.height(16.dp))

        TextField(value = password,
            onValueChange = {
                password = it
            },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                cursorColor = Color.Black,
                disabledLabelColor = Color.Gray,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                textColor = Color.Black
            ),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            placeholder = {
                Text(text = "Password", color = Color.Gray)
            })

        Button(
            onClick = {
                scope.launch {
                    viewModel.registerUser(email, password)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 30.dp, end = 30.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = buttonDarkBrown, contentColor = Color.White
            )
        ) {
            Text(text = "Sign Up")
        }

        Button(
            onClick = { goToSignIn() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 30.dp, end = 30.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = buttonLightBrown, contentColor = Color.Black
            )
        ) {
            Text(text = "Already Have an Account? Sign in!")
        }


        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            if (state.value?.isLoading == true) {
                CircularProgressIndicator()
            }
        }
        LaunchedEffect(key1 = state.value?.isSuccess) {
            scope.launch {
                if (state.value?.isSuccess?.isNotEmpty() == true) {
                    val sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("name", "true")
                    editor.apply()
                    val success = state.value?.isSuccess
                    onSignUp()
                    Toast.makeText(context, "$success", Toast.LENGTH_LONG).show()
                }
            }
        }
        LaunchedEffect(key1 = state.value?.isError) {
            scope.launch {
                if (state.value?.isError?.isNotEmpty() == true) {
                    val error = state.value?.isError
                    Toast.makeText(context, "$error", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
