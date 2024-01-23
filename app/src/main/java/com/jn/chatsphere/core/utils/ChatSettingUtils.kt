package com.jn.chatsphere.core.utils

import com.jn.chatsphere.domain.model.ChatSetting
import com.jn.chatsphere.domain.model.Message

object ChatSettingUtils {
    private val chatRegex = Regex("chatSetting=\\{(.+?)\\}")

    fun setupChat(message: Message, chatSettingPrevious: ChatSetting?): ChatSetting? {
        val matchResult = chatRegex.find(message.text)

        return matchResult?.toChatSetting() ?: chatSettingPrevious
    }

    private fun MatchResult.toChatSetting(): ChatSetting {
        val chatSettingContent = groupValues.getOrNull(1)
        var chatString = ChatSetting()

        chatSettingContent?.let { content ->
            val keyValuePair = content.split(", ")
            val settingMap = keyValuePair.map { pair ->
                val (key, value) = pair.split(":")
                key.trim() to value.trim()
            }

            val resultPair = settingMap.getOrNull(0)
            resultPair?.let {
                val key = it.first.removeSurrounding("\"")
                val value = it.second.removeSurrounding("\"")
                if (key == "title") {
                    chatString = chatString.copy(title = value)
                }
            }
        }
        return chatString
    }


    fun filterMessage(message: Message): Message {
        val regex = Regex("chatSetting=\\{\"title\":\"[^\"]+\"\\}\\s*\\n?")
        return message.copy(text = regex.replace(message.text, ""))
    }
}