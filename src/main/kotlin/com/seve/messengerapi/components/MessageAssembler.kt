package com.seve.messengerapi.components

import com.seve.messengerapi.helpers.MessageVO
import com.seve.messengerapi.models.Message
import org.springframework.stereotype.Component

@Component
class MessageAssembler {

    fun toMessageVO(message: Message): MessageVO {

        return MessageVO(message.id, message.sender?.id, message.recipient?.id,
                message.conversation?.id, message.body, message.createdAt.toString())
    }
}