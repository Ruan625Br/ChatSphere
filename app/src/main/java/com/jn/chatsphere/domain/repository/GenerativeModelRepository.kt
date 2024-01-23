package com.jn.chatsphere.domain.repository

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.InvalidStateException
import com.google.ai.client.generativeai.type.content
import com.jn.chatsphere.BuildConfig
import com.jn.chatsphere.core.utils.Prompt
import com.jn.chatsphere.domain.model.Message
import com.jn.chatsphere.domain.model.Participant
import javax.inject.Inject

class GenerativeModelRepository @Inject constructor() {
    private val generativeModel = GenerativeModel(
        modelName = "gemini-pro",
        apiKey = BuildConfig.apiKey
    )

    private val chat = generativeModel.startChat(
        history = listOf(
            content(
                role = "user"
            ) {
                text(Prompt.SETUP_CHAT_SETTING)
            },
            content(
                role = "model"
            ) {
                text(Prompt.SETUP_CHAT_SETTING_MODEL)
            }
        )
    )

    suspend fun generateContent(message: String, chatHistory: List<Content>): Message {

        chat.history.addAll(chatHistory)
      //  val response = chat.sendMessage(message)
        //val isError = response.text == null

        val errorText = "An error occurred while generating the message."
        val errorStateText = "Chat Instance has an active request."

        return try {
            val response = chat.sendMessage(message)
            val isError = response.text == null
            val text = if (isError) errorText else response.text!!
            val participant = if (isError) Participant.ERROR else
                Participant.MODEL
            Message(
                text = text,
                participant = participant,
            )
        } catch (e: Exception){
            val errorMsg = if (e is InvalidStateException) errorStateText else errorText
             Message(
                text = "$errorMsg\n\nError:\n${e.message}",
                participant = Participant.ERROR,
            )
        }


    }
}