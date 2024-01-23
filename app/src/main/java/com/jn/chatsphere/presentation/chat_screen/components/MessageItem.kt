package com.jn.chatsphere.presentation.chat_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ErrorOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jn.chatsphere.R
import com.jn.chatsphere.core.components.LoadingAnimation
import com.jn.chatsphere.domain.model.Message
import com.jn.chatsphere.domain.model.Participant

@Composable
fun MessageItem(
    modifier: Modifier = Modifier,
    message: Message
) {

    val participant = message.participant
    val isUser = participant == Participant.USER || participant == Participant.USER_ERROR
    val shape = RoundedCornerShape(
        topStart = if (isUser || participant == Participant.ERROR) 16.dp else 0.dp,
        topEnd = if (!isUser) 16.dp else 0.dp,
        bottomStart = 16.dp, bottomEnd = 16.dp
    )
    val horizontalAlignment = when (participant) {
        Participant.MODEL -> Alignment.Start
        Participant.ERROR -> Alignment.CenterHorizontally
        else -> Alignment.End

    }
    val bgColor = when {
        isUser -> MaterialTheme.colorScheme.primaryContainer
        message.isPending -> MaterialTheme.colorScheme.tertiaryContainer
        participant == Participant.MODEL -> MaterialTheme.colorScheme.secondaryContainer
        else -> MaterialTheme.colorScheme.errorContainer
    }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = horizontalAlignment
    ) {

        Column(
            modifier = modifier
                .padding(start = 10.dp, end = 10.dp)
                .background(color = bgColor, shape = shape),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (message.isPending) {
                    LoadingAnimation(
                        modifier = Modifier
                            .padding(8.dp),
                        circleSize = 8.dp,
                        circleColor = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }
                Text(
                    modifier = Modifier
                        .padding(5.dp),
                    text = message.text,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        if (participant == Participant.USER_ERROR){
            ErrorMessage(
                modifier = Modifier
                    .padding(end = 10.dp),
            )
        }
    }

}

@Composable
private fun ErrorMessage(
    modifier: Modifier = Modifier,
    error: String = stringResource(id = R.string.message_not_sent)
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Icon(
            modifier = Modifier
                .size(16.dp),
            imageVector = Icons.Rounded.ErrorOutline,
            tint = MaterialTheme.colorScheme.error,
            contentDescription = null
        )
        Text(
            text = error,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.error
        )
    }
}