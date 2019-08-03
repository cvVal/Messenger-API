package com.seve.messengerapi.repositories

import com.seve.messengerapi.models.Message
import org.springframework.data.repository.CrudRepository

interface MessageRepository : CrudRepository<Message, Long> {

    fun findByConversationId(conversationId: Long): List<Message>
}