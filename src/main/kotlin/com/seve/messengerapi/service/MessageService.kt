package com.seve.messengerapi.service

import com.seve.messengerapi.models.Message
import com.seve.messengerapi.models.User

interface MessageService {

    fun sendMessage(sender: User, recipientId: Long, messageText: String): Message
}