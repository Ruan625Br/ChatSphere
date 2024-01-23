package com.jn.chatsphere.presentation.chat_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jn.chatsphere.core.utils.ChatSettingUtils
import com.jn.chatsphere.domain.model.Message

@Composable
fun MessageList(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    paddingValues: PaddingValues,
    list: List<Message>
) {

    LazyColumn(
        state = state,
        contentPadding = paddingValues,
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        items(list, key = { it.id }) { msg ->
            MessageItem(
                modifier = Modifier,
                message = ChatSettingUtils.filterMessage(msg))
        }
    }
}

