package com.seve.messengerapi.service

import com.seve.messengerapi.exceptions.ConversationInvalidException
import com.seve.messengerapi.models.Conversation
import com.seve.messengerapi.models.User
import com.seve.messengerapi.repositories.ConversationRepository
import ma.glasnost.orika.MapperFacade
import org.springframework.stereotype.Service

@Service
class ConversationServiceImpl(
        val repository: ConversationRepository,
        val mapperFacade: MapperFacade) : ConversationService {


    override fun createConversation(userA: User, userB: User): Conversation {

        val conversation = Conversation()
        conversation.sender = userA
        conversation.recipient = userB

        repository.save(conversation)

        return conversation
    }

    override fun conversationExist(userA: User, userB: User): Boolean {

        return if (repository.findBySenderIdAndRecipientId(userA.id, userB.id) != null)
            true
        else
            repository.findBySenderIdAndRecipientId(userB.id, userA.id) != null
    }

    override fun getConversation(userA: User, userB: User): Conversation? {

        return when {

            repository.findBySenderIdAndRecipientId(userA.id, userB.id) != null ->
                repository.findBySenderIdAndRecipientId(userA.id, userB.id)

            repository.findBySenderIdAndRecipientId(userB.id, userB.id) != null ->
                repository.findBySenderIdAndRecipientId(userB.id, userB.id)

            else -> null
        }
    }

    override fun retrieveThread(conversationId: Long): Conversation {

        val conversation = repository.findById(conversationId)

        if (conversation.isPresent) {
            return conversation.get()
        }

        throw ConversationInvalidException("Invalid conversation id '$conversationId'.")
    }

    override fun listUserConversations(userId: Long): List<Conversation> {

        val conversationList: ArrayList<Conversation> = ArrayList()
        conversationList.addAll(repository.findBySenderId(userId))
        conversationList.addAll(repository.findByRecipientId(userId))

        return conversationList
    }

    override fun nameSecondParty(conversation: Conversation, userId: Long): String {

        return if (conversation.sender?.id == userId)
            conversation.recipient?.username as String
        else
            conversation.sender?.username as String
    }
}