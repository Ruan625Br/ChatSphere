package com.jn.chatsphere.presentation.chat_screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jn.chatsphere.domain.model.Message
import com.jn.chatsphere.presentation.chat_screen.components.ChatTextField
import com.jn.chatsphere.presentation.chat_screen.components.MessageList
import com.jn.chatsphere.utils.extensions.hasPendingMessage
import kotlinx.coroutines.launch

@Composable
fun ChatScreen(
    paddingValues: PaddingValues,
    state: ChatUiState = ChatUiState(),
    onClickSendMsg: (String) -> Unit,
) {

    ChatScreenContent(
        messages = state.chat.messages,
        paddingValues = paddingValues,
        onClickSendMsg = { onClickSendMsg(it) })
}

@Composable
fun ChatScreenContent(
    messages: List<Message>,
    paddingValues: PaddingValues,
    onClickSendMsg: (String) -> Unit
) {
    val lazyState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Column {
        MessageList(
            modifier = Modifier
                .padding(top = 10.dp)
                .weight(1f),
            state = lazyState,
            paddingValues = paddingValues,
            list = messages
        )

        ChatTextField(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                .animateContentSize(),
            onClickSendMsg = {
                if (!messages.hasPendingMessage()) {
                    onClickSendMsg(it)
                    coroutineScope.launch {
                        if (messages.isNotEmpty()) {
                            lazyState.animateScrollToItem(messages.size - 1, 0)
                        }
                    }
                }
            }
        )
    }
}