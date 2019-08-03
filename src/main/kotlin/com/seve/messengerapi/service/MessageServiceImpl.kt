package com.seve.messengerapi.service

import com.seve.messengerapi.exceptions.MessageEmptyException
import com.seve.messengerapi.exceptions.MessageRecipientInvalidException
import com.seve.messengerapi.models.Conversation
import com.seve.messengerapi.models.Message
import com.seve.messengerapi.models.User
import com.seve.messengerapi.repositories.ConversationRepository
import com.seve.messengerapi.repositories.MessageRepository
import com.seve.messengerapi.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class MessageServiceImpl(
        val messageRepo: MessageRepository,
        val conversationRepo: ConversationRepository,
        val conversationService: ConversationService,
        val userRepo: UserRepository) : MessageService {

    @Throws(MessageEmptyException::class, MessageRecipientInvalidException::class)
    override fun sendMessage(sender: User, recipientId: Long, messageText: String): Message {

        val optional = userRepo.findById(recipientId)

        if (optional.isPresent) {

            val recipient = optional.get()

            if (messageText.isNotEmpty()) {

                val conversation: Conversation = if (conversationService.conversationExist(sender, recipient)) {
                    conversationService.getConversation(sender, recipient)!!
                } else {
                    conversationService.createConversation(sender, recipient)
                }

                conversationRepo.save(conversation)

                val message = Message()
                message.sender = sender
                message.recipient = recipient
                message.body = messageText
                message.conversation = conversation

                messageRepo.save(message)

                return message
            } else {
                throw MessageRecipientInvalidException("The recipientId id '$recipientId is invalid.")
            }
        }
        throw MessageEmptyException()
    }
}