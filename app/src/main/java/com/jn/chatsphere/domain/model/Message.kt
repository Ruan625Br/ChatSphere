package com.jn.chatsphere.domain.model

import java.util.UUID

enum class Participant {
    USER,
    MODEL,
    ERROR,
    USER_ERROR
}

data class Message(
    val id: UUID = UUID.randomUUID(),
    val text: String = "",
    val participant: Participant = Participant.MODEL,
    val isPending: Boolean = false
){
    override fun toString(): String {
        return "id: $id\ntext: $text\nparticipant: $participant\nisPending: $isPending"
    }
}

val loadingMessage = Message(
    text = "Gerando Mensagem",
    participant = Participant.MODEL,
    isPending = true
)

