package com.example.android_bootcamp.presentation.screen.register

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.android_bootcamp.R
import com.example.android_bootcamp.presentation.register.RegisterViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun RegisterScreenRoute(
    onBackPress: () -> Unit,
    onNavigateBackToLogin: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
) {
    val state by viewModel.registerState.collectAsStateWithLifecycle()

    RegisterScreen(
        state = state,
        onRegister = viewModel::register,
        onEmailChange = viewModel::updateEmail,
        onPasswordChange = viewModel::updatePassword,
        onRepeatPasswordChange = viewModel::updateRepeatPassword,
        onTogglePasswordVisibility = viewModel::togglePasswordVisibility,
        onBackPress = viewModel::navigateWithBackIcon,
        onToggleRepeatPasswordVisibility = viewModel::toggleRepeatPasswordVisibility,
        onFullNameChange = viewModel::updateFullName
    )

    LaunchedEffect(Unit) {
        viewModel.uiEvents.collectLatest { event ->
            when (event) {
                RegisterUiEvents.NavigateBackToLogin -> onNavigateBackToLogin()
                RegisterUiEvents.OnBackPress -> onBackPress()
                is RegisterUiEvents.ShowError ->
                    snackbarHostState.showSnackbar(event.message)
            }
        }
    }
}


@Composable
fun RegisterScreen(
    state: RegisterUiState,
    onFullNameChange: (String) -> Unit,
    onRegister: () -> Unit,
    onEmailChange: (email: String) -> Unit,
    onPasswordChange: (password: String) -> Unit,
    onRepeatPasswordChange: (repeatPassword: String) -> Unit,
    onTogglePasswordVisibility: () -> Unit,
    onBackPress: () -> Unit,
    onToggleRepeatPasswordVisibility: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_back),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 30.dp, top = 40.dp)
                .clickable { onBackPress() },
            tint = colorResource(R.color.sky_blue)
        )
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(
                text = "Register",
                fontSize = 30.sp,
                fontFamily = FontFamily.Serif
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Create your new account",
                fontSize = 18.sp,
                fontFamily = FontFamily.Serif
            )
            Spacer(modifier = Modifier.height(70.dp))
            TextField(
                value = state.fullName,
                onValueChange = { newFullName -> onFullNameChange(newFullName) },
                placeholder = {
                    Text(
                        "Full name",
                        fontFamily = FontFamily.Serif,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
                    .height(60.dp)
                    .border(1.dp, colorResource(R.color.sky_blue), shape = RoundedCornerShape(20)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    cursorColor = Color.Black
                ), leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_user),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 16.dp, end = 8.dp)
                            .size(28.dp),
                        tint = colorResource(R.color.sky_blue)


                    )
                }

            )
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                value = state.email,
                onValueChange = { newEmail -> onEmailChange(newEmail) },
                placeholder = {
                    Text(
                        "Email",
                        fontFamily = FontFamily.Serif,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
                    .height(60.dp)
                    .border(1.dp, colorResource(R.color.sky_blue), shape = RoundedCornerShape(20)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    cursorColor = Color.Black
                ), leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_email),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 16.dp, end = 8.dp)
                            .size(28.dp),
                        tint = colorResource(R.color.sky_blue)


                    )
                }

            )
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                value = state.password,
                onValueChange = { newPassword -> onPasswordChange(newPassword) },
                placeholder = {
                    Text(
                        "Password",
                        fontFamily = FontFamily.Serif,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                },
                visualTransformation = if (state.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
                    .height(60.dp)
                    .border(1.dp, colorResource(R.color.sky_blue), shape = RoundedCornerShape(20)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    cursorColor = Color.Black
                ), leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_pass),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 16.dp, end = 8.dp)
                            .size(28.dp),
                        tint = colorResource(R.color.sky_blue)
                    )
                },
                trailingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_hide_view),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .size(26.dp)
                            .clickable { onTogglePasswordVisibility() },
                        tint = colorResource(R.color.black).copy(alpha = 0.7f)
                    )
                }

            )
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                value = state.repeatPassword,
                onValueChange = { newRepeatedPassword -> onRepeatPasswordChange(newRepeatedPassword) },
                placeholder = {
                    Text(
                        "Confirm Password",
                        fontFamily = FontFamily.Serif,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                },
                visualTransformation = if (state.isRepeatPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
                    .height(60.dp)
                    .border(1.dp, colorResource(R.color.sky_blue), shape = RoundedCornerShape(20)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    cursorColor = Color.Black
                ), leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_pass),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 16.dp, end = 8.dp)
                            .size(28.dp),
                        tint = colorResource(R.color.sky_blue)
                    )
                },
                trailingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_hide_view),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .size(26.dp)
                            .clickable { onToggleRepeatPasswordVisibility() },
                        tint = colorResource(R.color.black).copy(alpha = 0.7f)
                    )
                }
            )
            Spacer(modifier = Modifier.height(80.dp))
            Button(
                onClick = { onRegister() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.sky_blue)
                )
            ) {
                Text(
                    text = "Sign Up",
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Serif
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(state = RegisterUiState(),
        onFullNameChange = {},
        onRegister = {},
        onEmailChange = {},
        onPasswordChange = {},
        onRepeatPasswordChange = {},
        onTogglePasswordVisibility = {},
        onBackPress = {},
        onToggleRepeatPasswordVisibility = {})
}