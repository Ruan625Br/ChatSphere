package com.jn.chatsphere.utils.extensions

import com.jn.chatsphere.domain.model.Message


fun List<Message>.hasPendingMessage(): Boolean = any { it.isPending }