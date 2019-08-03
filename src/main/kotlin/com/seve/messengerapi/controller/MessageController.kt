package com.seve.messengerapi.controller

import com.seve.messengerapi.components.MessageAssembler
import com.seve.messengerapi.helpers.MessageVO
import com.seve.messengerapi.models.User
import com.seve.messengerapi.repositories.UserRepository
import com.seve.messengerapi.service.MessageServiceImpl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/messages")
class MessageController(val messageServiceImpl: MessageServiceImpl,
                        val userRepository: UserRepository,
                        val messageAssembler: MessageAssembler) {

    @PostMapping
    fun create(@RequestBody messageDetails: MessageRequest, request: HttpServletRequest): ResponseEntity<MessageVO> {

        val principal = request.userPrincipal
        val sender = userRepository.findByUsername(principal.name) as User
        val message = messageServiceImpl.sendMessage(sender, messageDetails.recipientId, messageDetails.message)

        return ResponseEntity.ok(messageAssembler.toMessageVO(message))
    }

    data class MessageRequest(val recipientId: Long, val message: String)
}