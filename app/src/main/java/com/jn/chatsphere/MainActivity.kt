package com.jn.chatsphere

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jn.chatsphere.core.components.CustomAppBar
import com.jn.chatsphere.presentation.chat_screen.ChatScreen
import com.jn.chatsphere.presentation.chat_screen.ChatViewModel
import com.jn.chatsphere.presentation.chat_screen.components.ChatNavigationDrawer
import com.jn.chatsphere.ui.theme.ChatSphereTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val chatViewModel: ChatViewModel = hiltViewModel()
            val chatUiState by chatViewModel.state.collectAsStateWithLifecycle()

            ChatSphereTheme(
                dynamicColor = false
            ) {
                ChatNavigationDrawer(
                    chatUiState = chatUiState,
                    onClickChat = { chatViewModel.setCurrentChat(it) },
                    onClickNewChat = { chatViewModel.newChat() }) {
                    Scaffold(topBar = {
                        CustomAppBar(
                            chatSetting = chatUiState.chat.chatSetting
                        )
                    }) { paddingValues ->
                        ChatScreen(paddingValues = paddingValues,
                            state = chatUiState,
                            onClickSendMsg = { chatViewModel.sendMessage(it) })
                    }
                }
            }
        }
    }
}