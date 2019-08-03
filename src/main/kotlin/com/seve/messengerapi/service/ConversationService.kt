package com.seve.messengerapi.service

import com.seve.messengerapi.models.Conversation
import com.seve.messengerapi.models.User

interface ConversationService {

    fun createConversation(userA: User, userB: User): Conversation
    fun conversationExist(userA: User, userB: User): Boolean
    fun getConversation(userA: User, userB: User): Conversation?
    fun retrieveThread(conversationId: Long): Conversation
    fun listUserConversations(userId: Long): List<Conversation>
    fun nameSecondParty(conversation: Conversation, userId: Long): String
}