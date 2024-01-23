package com.jn.chatsphere.core.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.jn.chatsphere.domain.model.ChatSetting

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAppBar(
    chatSetting: ChatSetting?
) {
    TopAppBar(
        title = {
            Text(text = chatSetting?.title ?: ChatSetting().title)
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)
        )
    )
}