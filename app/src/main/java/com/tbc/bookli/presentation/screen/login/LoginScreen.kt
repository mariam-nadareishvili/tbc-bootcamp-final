package com.tbc.bookli.presentation.screen.login


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
import com.tbc.bookli.R
import com.tbc.bookli.presentation.login.LoginViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreenRoute(
    onNavigateToHome: () -> Unit,
    onNavigateToRegister: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
) {
    val state by viewModel.loginState.collectAsStateWithLifecycle()

    LoginScreen(
        state = state,
        onLogin = viewModel::login,
        onNavigateToRegister = viewModel::navigateToRegister,
        onEmailChange = viewModel::updateEmail,
        onPasswordChange = viewModel::updatePassword,
        onRememberMeChange = viewModel::updateRememberMe,
        onTogglePasswordVisibility = viewModel::togglePasswordVisibility
    )

    LaunchedEffect(Unit) {

        viewModel.uiEvents.collectLatest { event ->
            when (event) {
                is LoginUiEvents.ShowError -> {
                    snackbarHostState.showSnackbar(event.message)
                }

                is LoginUiEvents.NavigateToHomeScreen -> {
                    onNavigateToHome()
                }

                is LoginUiEvents.NavigateToRegister -> {
                    onNavigateToRegister()
                }

            }
        }
    }

}

@Composable
fun LoginScreen(
    state: LoginUiState,
    onLogin: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRememberMeChange: (Boolean) -> Unit,
    onTogglePasswordVisibility: () -> Unit,
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
            painter = painterResource(R.drawable.app_logo),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(40.dp))
        TextField(
            value = state.email,
            onValueChange = { newEmail ->
                onEmailChange(newEmail)
            },
            placeholder = {
                Text(
                    modifier = Modifier.padding(top = 2.dp),
                    text = stringResource(R.string.email),
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
                    painter = painterResource(R.drawable.ic_email),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 16.dp, end = 8.dp)
                        .size(30.dp),
                    tint = colorResource(R.color.sky_blue)
                )
            }
        )
        Spacer(modifier = Modifier.height(40.dp))
        TextField(
            value = state.password,
            onValueChange = { newPassword -> onPasswordChange(newPassword) },
            placeholder = {
                Text(
                    text = stringResource(R.string.password),
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
                    painter = painterResource(R.drawable.ic_pass),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 16.dp, end = 8.dp)
                        .size(30.dp),
                    tint = colorResource(R.color.sky_blue)
                )

            },
            trailingIcon = {
                Icon(
                    painter = painterResource(if (state.isPasswordVisible) R.drawable.ic_show_view else R.drawable.ic_hide_view),
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
        Row(
            verticalAlignment = Alignment.CenterVertically,

            ) {
            Checkbox(
                checked = state.isRememberMeChecked,
                onCheckedChange = { isChecked -> onRememberMeChange(isChecked) },
                enabled = true
            )
            Text(
                text = stringResource(R.string.remember_me),
                fontSize = 14.sp,
                fontFamily = FontFamily.Serif
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = { onLogin() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.sky_blue),
            )
        )
        {
            Text(
                text = stringResource(R.string.log_in),
                fontSize = 20.sp,
                fontFamily = FontFamily.Serif
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Row(verticalAlignment = Alignment.CenterVertically)
        {
            Text(
                text = stringResource(R.string.don_t_you_have_an_account_yet),
                fontSize = 14.sp,
                fontFamily = FontFamily.Serif
            )
            Text(
                text = stringResource(R.string.sign_up),
                fontSize = 18.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 5.dp)
                    .clickable { onNavigateToRegister() },
                color = colorResource(R.color.sky_blue).copy(alpha = 2f)
            )
        }

    }

}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(state = LoginUiState(),
        onLogin = {},
        onNavigateToRegister = {},
        onEmailChange = {},
        onPasswordChange = {},
        onRememberMeChange = {},
        onTogglePasswordVisibility = {})
}