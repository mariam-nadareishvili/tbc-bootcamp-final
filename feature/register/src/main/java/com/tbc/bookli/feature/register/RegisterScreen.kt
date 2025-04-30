package com.tbc.bookli.feature.register

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tbc.bookli.core.ui.SkyBlue
import com.tbc.bookli.core.ui.components.BookliLoader
import com.tbc.bookli.core.common.R as CommonR
import com.tbc.bookli.feature.register.RegisterViewModel.RegisterUiEvent
import kotlinx.coroutines.flow.collectLatest

@Composable
fun RegisterScreenRoute(
    onBackPress: () -> Unit,
    onNavigateBackToLogin: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    RegisterScreen(
        state = state,
        onEvent = viewModel::onEvent
    )

    LaunchedEffect(Unit) {
        viewModel.uiEvents.collectLatest { event ->
            when (event) {
                RegisterUiEvent.NavigateBackToLogin -> onNavigateBackToLogin()
                RegisterUiEvent.OnBackPress -> onBackPress()
            }
        }
    }
}


@Composable
fun RegisterScreen(
    state: RegisterUiState,
    onEvent: (RegisterEvent) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
            contentDescription = null,
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp)
                .clickable { onEvent(RegisterEvent.NavigateBack) },
            tint = Color.DarkGray
        )
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(
                text = stringResource(CommonR.string.register),
                fontSize = 30.sp,
                fontFamily = FontFamily.Serif
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(CommonR.string.create_your_new_account),
                fontSize = 18.sp,
                fontFamily = FontFamily.Serif
            )
            Spacer(modifier = Modifier.height(70.dp))
            TextField(
                value = state.fullName,
                onValueChange = { onEvent(RegisterEvent.FullNameChanged(it)) },
                placeholder = {
                    Text(
                        stringResource(CommonR.string.full_name),
                        fontFamily = FontFamily.Serif,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
                    .height(60.dp)
                    .border(1.dp, SkyBlue, shape = RoundedCornerShape(20)),
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
                        painter = painterResource(CommonR.drawable.ic_user),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 16.dp, end = 8.dp)
                            .size(28.dp),
                        tint = SkyBlue


                    )
                }

            )
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                value = state.email,
                onValueChange = { onEvent(RegisterEvent.EmailChanged(it)) },
                placeholder = {
                    Text(
                        stringResource(CommonR.string.email),
                        fontFamily = FontFamily.Serif,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
                    .height(60.dp)
                    .border(1.dp, SkyBlue, shape = RoundedCornerShape(20)),
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
                        painter = painterResource(CommonR.drawable.ic_email),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 16.dp, end = 8.dp)
                            .size(28.dp),
                        tint = SkyBlue


                    )
                }

            )
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                value = state.password,
                onValueChange = { onEvent(RegisterEvent.PasswordChanged(it)) },
                placeholder = {
                    Text(
                        stringResource(CommonR.string.password),
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
                    .border(1.dp, SkyBlue, shape = RoundedCornerShape(20)),
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
                        painter = painterResource(CommonR.drawable.ic_pass),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 16.dp, end = 8.dp)
                            .size(28.dp),
                        tint = SkyBlue
                    )
                },
                trailingIcon = {
                    Icon(
                        painter = painterResource(CommonR.drawable.ic_hide_view),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .size(26.dp)
                            .clickable { onEvent(RegisterEvent.TogglePasswordVisibility) },
                        tint = Color.Black.copy(alpha = 0.7f)
                    )
                }

            )
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                value = state.repeatPassword,
                onValueChange = { onEvent(RegisterEvent.RepeatPasswordChanged(it)) },
                placeholder = {
                    Text(
                        stringResource(CommonR.string.confirm_password),
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
                    .border(1.dp, SkyBlue, shape = RoundedCornerShape(20)),
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
                        painter = painterResource(CommonR.drawable.ic_pass),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 16.dp, end = 8.dp)
                            .size(28.dp),
                        tint = SkyBlue
                    )
                },
                trailingIcon = {
                    Icon(
                        painter = painterResource(CommonR.drawable.ic_hide_view),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .size(26.dp)
                            .clickable { onEvent(RegisterEvent.ToggleRepeatPasswordVisibility) },
                        tint = Color.Black.copy(alpha = 0.7f)
                    )
                }
            )
            Spacer(modifier = Modifier.height(80.dp))
            Button(
                onClick = { onEvent(RegisterEvent.SubmitRegister) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SkyBlue
                )
            ) {
                Text(
                    text = stringResource(CommonR.string.sign_up),
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Serif
                )
            }

        }
    }

    if (state.isLoading) {
        BookliLoader()
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(
        state = RegisterUiState(),
        onEvent = {}
    )
}