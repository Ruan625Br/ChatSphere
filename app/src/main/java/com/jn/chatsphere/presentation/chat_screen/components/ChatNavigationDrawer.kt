package com.jn.chatsphere.presentation.chat_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Message
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jn.chatsphere.data.local.entities.Chat
import com.jn.chatsphere.presentation.chat_screen.ChatUiState
import com.jn.chatsphere.ui.theme.Shapes

@Composable
fun ChatNavigationDrawer(
    modifier: Modifier = Modifier,
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    chatUiState: ChatUiState,
    onClickChat: (Chat) -> Unit,
    onClickNewChat: () -> Unit,
    content: @Composable () -> Unit
) {

    ModalNavigationDrawer(
        modifier = modifier,
        drawerState = drawerState,
        drawerContent = {
        ModalDrawerSheet {
            Column(
                modifier = Modifier
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {

                ButtonNewChat(
                    onClick = onClickNewChat
                )
                DrawerContent(
                    chats = chatUiState.chatList,
                    chatSelected = chatUiState.chat,
                    onClickChat = { onClickChat(it) })
            }
        }
    }) {
        content()
    }
}


@Composable
fun DrawerContent(
    chats: List<Chat>, chatSelected: Chat?, onClickChat: (Chat) -> Unit
) {

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        items(chats, key = { it.id}) { chat ->
            DrawerItem(chat = chat,
                selected = chatSelected == chat,
                onClick = { onClickChat(chat) })
        }
    }
}

@Composable
fun DrawerItem(
    chat: Chat, selected: Boolean, onClick: () -> Unit
) {
    NavigationDrawerItem(
        icon = {
               Icon(imageVector = Icons.AutoMirrored.Rounded.Message, contentDescription = null)
        },
        label = {
        Text(text = chat.chatSetting?.title.toString())
    },
        shape = Shapes.large,
        selected = selected, onClick = onClick)
}


@Composable
fun ButtonNewChat(
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth(),
        shape = Shapes.large,
        onClick = onClick) {
        Text(text = "New chat")
    }
}