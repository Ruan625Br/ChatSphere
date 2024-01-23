package com.jn.chatsphere.presentation.chat_screen

import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.content
import com.jn.chatsphere.data.local.entities.Chat
import com.jn.chatsphere.domain.model.Message
import com.jn.chatsphere.domain.model.Participant

data class ChatUiState(
    val chatList: List<Chat> = emptyList(),
    val chat: Chat = chatList.firstOrNull() ?: Chat()
)

fun List<Message>.toContent(): List<Content> {
    val filteredList = filter { !it.isPending && it.participant != Participant.ERROR && it.participant != Participant.USER_ERROR }

    return filteredList.map { msg ->
        val role = if (msg.participant == Participant.USER) "user" else "model"
        content(role = role) {
            text(msg.text)
        }
    }
}