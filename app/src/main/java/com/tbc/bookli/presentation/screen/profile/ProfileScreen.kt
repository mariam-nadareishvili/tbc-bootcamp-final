package com.tbc.bookli.presentation.screen.profile

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tbc.bookli.R
import com.tbc.bookli.presentation.screen.profile.ProfileViewModel.ProfileUiEvents
import kotlinx.coroutines.flow.collectLatest


@Composable
fun ProfileScreenRoute(
    viewmodel: ProfileViewModel = hiltViewModel(),
    onNavigateToLogin: () -> Unit
) {
    val state by viewmodel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewmodel.events.collectLatest { event ->
            when (event) {
                ProfileUiEvents.NavigateToLogin -> onNavigateToLogin()
            }
        }
    }

    if (state.showDialog) {
        AvatarSelectionDialog(
            onAvatarSelected = {
                viewmodel.onEvent(ProfileEvent.HideDialog)
                viewmodel.onEvent(ProfileEvent.UpdateUserAvatar(it))
            },
            onDismissRequest = {
                viewmodel.onEvent(ProfileEvent.HideDialog)
            }
        )
    }

    ProfileScreen(
        state = state,
        onEvent = viewmodel::onEvent
    )
}


@Composable
fun ProfileScreen(
    state: ProfileUiState,
    onEvent: (ProfileEvent) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        Image(
            painter = painterResource(state.userInfo?.avatar?.drawableRes ?: R.drawable.ic_rabbit),
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(70.dp))
                .clickable { onEvent(ProfileEvent.ShowDialog) },
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = state.userInfo?.fullName ?: "",
            fontSize = 18.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = state.userInfo?.email ?: "",
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(80.dp))
        ProfileSection(
            leadingIconId = R.drawable.ic_web,
            textId = R.string.language,
            trailingContent = {
                Image(
                    painter = painterResource(
                        if (state.currentLanguage == LanguageType.GEORGIAN.language) R.drawable.ic_uk
                        else R.drawable.ic_geo
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable { onEvent(ProfileEvent.ToggleLanguage) }
                )
            }
        )
        Spacer(modifier = Modifier.height(28.dp))
        ProfileSection(
            leadingIconId = R.drawable.ic_dark_mode,
            textId = R.string.dark_mode,
            trailingContent = {
                Switch(
                    checked = state.isDarkMode,
                    onCheckedChange = { onEvent(ProfileEvent.ToggleTheme(it)) }
                )
            }
        )
        Spacer(modifier = Modifier.height(40.dp))
        HorizontalDivider(modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onEvent(ProfileEvent.NavigateToLogin) },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_log_out),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .size(50.dp),
                tint = Color.Red
            )
            Text(
                text = stringResource(R.string.log_out),
                fontSize = 20.sp,
                color = Color.Red,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
    }
}


@Composable
fun ProfileSection(
    @DrawableRes leadingIconId: Int,
    @StringRes textId: Int,
    trailingContent: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(leadingIconId),
            contentDescription = null,
            modifier = Modifier
                .size(30.dp)
        )
        Spacer(modifier = Modifier.width(24.dp))
        Text(
            text = stringResource(textId),
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.weight(1f))
        trailingContent()
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(
        state = ProfileUiState(),
        onEvent = {},
    )
}