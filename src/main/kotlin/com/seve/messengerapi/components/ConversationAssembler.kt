package com.seve.messengerapi.components

import com.seve.messengerapi.helpers.ConversationListVO
import com.seve.messengerapi.helpers.ConversationVO
import com.seve.messengerapi.helpers.MessageVO
import com.seve.messengerapi.models.Conversation
import com.seve.messengerapi.service.ConversationServiceImpl
import org.springframework.stereotype.Component

@Component
class ConversationAssembler(val conversationService: ConversationServiceImpl,
                            val messageAssembler: MessageAssembler) {

    fun toConversationVO(conversation: Conversation, userId: Long): ConversationVO {

        val conversationMessages: ArrayList<MessageVO> = ArrayList()
        conversation.messages!!.mapTo(conversationMessages) {messageAssembler.toMessageVO(it)}

        return ConversationVO(conversation.id, conversationService.nameSecondParty(conversation, userId), conversationMessages)
    }

    fun toConversationListVO(conversations: List<Conversation>, userId: Long): ConversationListVO {

        val conversationVOList = conversations.map { toConversationVO(it, userId) }

        return ConversationListVO(conversationVOList)
    }
}