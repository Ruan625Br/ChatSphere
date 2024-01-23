package com.jn.chatsphere.presentation.chat_screen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp

@Composable
fun ChatTextField(
    modifier: Modifier = Modifier,
    onClickSendMsg: (String) -> (Unit)
) {
    var value by rememberSaveable {
        mutableStateOf("")
    }
    var collapsed by remember {
        mutableStateOf(false)
    }
    val textFieldWidth by animateDpAsState(
        targetValue = if (collapsed) 50.dp else 500.dp,
        animationSpec = tween(300),
        label = "")
    val keyboardController = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        modifier = modifier
            .width(textFieldWidth),
        value = value,
        onValueChange = { value = it },
        label = {
            if (!collapsed) {
                Text(text = "Message")
            }
        },

        leadingIcon = {
            IconButton(onClick = {
                keyboardController?.hide()
                collapsed = !collapsed
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowForwardIos,
                    contentDescription = null
                )
            }
        },
        trailingIcon = {
            AnimatedVisibility(visible = value.isNotBlank()) {
                IconButton(onClick = {
                    onClickSendMsg(value)
                    value = ""
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.Send,
                        contentDescription = "Send message"
                    )
                }
            }
        },
        shape = RoundedCornerShape(20.dp)
    )
}