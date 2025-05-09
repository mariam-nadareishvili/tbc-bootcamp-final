package com.tbc.bookli.feature.login


import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.text.font.FontWeight
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
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreenRoute(
    onNavigateToHome: () -> Unit,
    onNavigateToRegister: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state by viewModel.loginState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.uiEvents.collectLatest { event ->
            when (event) {
                LoginUiEvents.NavigateToHomeScreen -> onNavigateToHome()
                LoginUiEvents.NavigateToRegister -> onNavigateToRegister()
            }
        }
    }

    LoginScreen(
        state = state,
        onEvent = viewModel::onEvent
    )
}


@Composable
fun LoginScreen(
    state: LoginUiState,
    onEvent: (LoginEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Spacer(modifier = Modifier.height(70.dp))
        Image(
            modifier = Modifier
                .height(300.dp),
            painter = painterResource(CommonR.drawable.ic_logo),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(40.dp))
        TextField(
            value = state.email,
            onValueChange = { onEvent(LoginEvent.EmailChanged(it)) },
            placeholder = {
                Text(
                    modifier = Modifier.padding(top = 2.dp),
                    text = stringResource(CommonR.string.email),
                    fontFamily = FontFamily.Serif
                )
            },
            modifier = Modifier
                .padding(horizontal = 30.dp)
                .fillMaxWidth()
                .height(60.dp)
                .border(1.dp, color = Color.Gray, shape = RoundedCornerShape(20f)),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = Color.Black,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            ),
            leadingIcon = {
                Icon(
                    painter = painterResource(CommonR.drawable.ic_email),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 16.dp, end = 8.dp)
                        .size(30.dp),
                    tint = SkyBlue
                )
            }
        )
        Spacer(modifier = Modifier.height(40.dp))
        TextField(
            value = state.password,
            onValueChange = { onEvent(LoginEvent.PasswordChanged(it)) },
            placeholder = {
                Text(
                    text = stringResource(CommonR.string.password),
                    fontFamily = FontFamily.Serif,
                    modifier = Modifier.padding(top = 2.dp)
                )
            },
            visualTransformation = if (state.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .padding(horizontal = 30.dp)
                .fillMaxWidth()
                .height(60.dp)
                .border(1.dp, color = Color.Gray, shape = RoundedCornerShape(20f)),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = Color.Black,
                focusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            leadingIcon = {
                Icon(
                    painter = painterResource(CommonR.drawable.ic_pass),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 16.dp, end = 8.dp)
                        .size(30.dp),
                    tint = SkyBlue
                )

            },
            trailingIcon = {
                Icon(
                    painter = painterResource(if (state.isPasswordVisible) CommonR.drawable.ic_show_view else CommonR.drawable.ic_hide_view),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .size(26.dp)
                        .clickable { onEvent(LoginEvent.TogglePasswordVisibility) },
                    tint = Color.Black.copy(alpha = 0.7f)
                )
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,

            ) {
            Checkbox(
                checked = state.isRememberMeChecked,
                onCheckedChange = { onEvent(LoginEvent.RememberMeChanged(it)) },
                enabled = true
            )
            Text(
                text = stringResource(CommonR.string.remember_me),
                fontSize = 14.sp,
                fontFamily = FontFamily.Serif
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = { onEvent(LoginEvent.Login) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = SkyBlue,
            )
        )
        {
            Text(
                text = stringResource(CommonR.string.log_in),
                fontSize = 20.sp,
                fontFamily = FontFamily.Serif
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Row(verticalAlignment = Alignment.CenterVertically)
        {
            Text(
                text = stringResource(CommonR.string.don_t_you_have_an_account_yet),
                fontSize = 14.sp,
                fontFamily = FontFamily.Serif
            )
            Text(
                text = stringResource(CommonR.string.sign_up),
                fontSize = 18.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 5.dp)
                    .clickable { onEvent(LoginEvent.NavigateToRegister) },
                color = SkyBlue.copy(alpha = 2f)
            )
        }

    }

    if (state.isLoading) {
        BookliLoader()
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        state = LoginUiState(),
        onEvent = {}
    )
}