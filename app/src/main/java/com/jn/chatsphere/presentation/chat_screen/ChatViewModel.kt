package com.jn.chatsphere.presentation.chat_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jn.chatsphere.core.utils.ChatSettingUtils
import com.jn.chatsphere.data.local.entities.Chat
import com.jn.chatsphere.data.local.entities.ChatEntity
import com.jn.chatsphere.data.local.entities.toChat
import com.jn.chatsphere.data.local.entities.toChatEntity
import com.jn.chatsphere.data.local.repository.ChatRepository
import com.jn.chatsphere.domain.model.Message
import com.jn.chatsphere.domain.model.Participant
import com.jn.chatsphere.domain.model.loadingMessage
import com.jn.chatsphere.domain.repository.GenerativeModelRepository
import com.jn.chatsphere.utils.extensions.hasPendingMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val modelRepository: GenerativeModelRepository,
    private val chatRepository: ChatRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ChatUiState())
    val state = _state.asStateFlow()

    init {
        loadChatList()
        newChat()
    }

    fun sendMessage(message: String) {
        viewModelScope.launch {
            val previousMessages = _state.value.chat.messages
            val messages = mutableListOf<Message>()
            val userMessage = Message(
                text = message, participant = Participant.USER
            )

            messages.apply {
                addAll(previousMessages)
                add(userMessage)
                add(loadingMessage)
            }
            updateMessages(messages, Message())
            chatRepository.upsert(_state.value.chat.toChatEntity())

            val modelMessage = modelRepository.generateContent(
                message = message, chatHistory = previousMessages.toContent()
            )

            messages.remove(loadingMessage)
            messages.add(modelMessage)

            if (modelMessage.participant == Participant.ERROR) {
                val index = messages.size - 2
                val msg = messages[index].copy(participant = Participant.USER_ERROR)
                messages[index] = msg
            }

            updateMessages(messages, modelMessage)
            chatRepository.upsert(_state.value.chat.toChatEntity())
        }

    }

    private fun updateMessages(messages: List<Message>, modelMessage: Message) {
        val chatSetting = _state.value.chat.chatSetting
        val chat = _state.value.chat.copy(
            messages = messages,
            chatSetting = ChatSettingUtils.setupChat(modelMessage, chatSetting))

        _state.update {
            it.copy(chat = chat)
        }
        setCurrentChat(chat)

    }


    private fun loadChatList() {
        viewModelScope.launch {
            chatRepository.getAllChat().collectLatest { listChatEntity ->
                _state.update { listChat ->
                    val list = listChatEntity.map { it.toChat() }.reversed()
                    listChat.copy(chatList = list)
                }
            }
        }
    }

    fun newChat() {
        val chatEntity = ChatEntity()

        viewModelScope.launch {
            if (!_state.value.chat.messages.hasPendingMessage()) {
                chatRepository.upsert(chatEntity)
                delay(500)
                setCurrentChat(_state.value.chatList.first())
            }
        }
    }

    fun setCurrentChat(chat: Chat) {
        if (!_state.value.chat.messages.hasPendingMessage()) {
            _state.update {
                it.copy(chat = chat)
            }
        }

        Log.e("Chat", "${chat.id}")
    }
}