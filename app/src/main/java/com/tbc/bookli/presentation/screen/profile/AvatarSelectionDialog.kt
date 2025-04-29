package com.tbc.bookli.presentation.screen.profile

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.tbc.bookli.presentation.screen.AvatarType

@Composable
fun AvatarSelectionDialog(
    onAvatarSelected: (AvatarType) -> Unit,
    onDismissRequest: () -> Unit,
    avatars: List<AvatarType> = AvatarType.entries.toList()
) {
    var selectedAvatar by remember { mutableStateOf<AvatarType?>(null) }

    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 8.dp
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Select Your Avatar", style = MaterialTheme.typography.titleLarge)

                Spacer(modifier = Modifier.height(32.dp))

                FlowRow(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    maxItemsInEachRow = 4
                ) {
                    avatars.forEach { avatar ->
                        val isSelected = selectedAvatar == avatar

                        val scale by animateFloatAsState(
                            targetValue = if (isSelected) 1.25f else 1f,
                            label = "AvatarScaleAnimation"
                        )

                        Box(
                            contentAlignment = Alignment.BottomEnd,
                            modifier = Modifier
                                .graphicsLayer {
                                    scaleX = scale
                                    scaleY = scale
                                }
                                .size(56.dp)
                                .clip(CircleShape)
                                .border(
                                    width = if (isSelected) 4.dp else 2.dp,
                                    color = if (isSelected) Color.Green else Color.Gray,
                                    shape = CircleShape
                                )
                                .clickable { selectedAvatar = avatar }
                        ) {
                            Image(
                                painter = painterResource(id = avatar.drawableRes),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )

                            if (isSelected) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = Color.Green,
                                    modifier = Modifier
                                        .size(20.dp)
                                        .offset(x = (-6).dp, y = (-6).dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(48.dp))

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp),
                    onClick = {
                        selectedAvatar?.let { onAvatarSelected(it) }
                        onDismissRequest()
                    },
                    enabled = selectedAvatar != null
                ) {
                    Text(text = "Select")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AvatarSelectionDialogPreview() {
    AvatarSelectionDialog(
        avatars = AvatarType.entries.toList(),
        onAvatarSelected = {},
        onDismissRequest = {},
    )
}