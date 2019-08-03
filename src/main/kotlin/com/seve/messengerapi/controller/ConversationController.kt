package com.seve.messengerapi.controller

import com.seve.messengerapi.components.ConversationAssembler
import com.seve.messengerapi.helpers.ConversationListVO
import com.seve.messengerapi.helpers.ConversationVO
import com.seve.messengerapi.models.User
import com.seve.messengerapi.repositories.UserRepository
import com.seve.messengerapi.service.ConversationServiceImpl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/conversations")
class ConversationController(val conversationService: ConversationServiceImpl,
                             val userRepository: UserRepository,
                             val conversationAssembler: ConversationAssembler) {

    @GetMapping
    fun listConversations(request: HttpServletRequest): ResponseEntity<ConversationListVO> {

        val user = userRepository.findByUsername(request.userPrincipal.name) as User
        val conversations = conversationService.listUserConversations(user.id)

        return ResponseEntity.ok(conversationAssembler.toConversationListVO(conversations, user.id))
    }

    @GetMapping
    @RequestMapping("/{conversationId}")
    fun showConversation(@PathVariable(name = "conversationId") conversationId: Long, request: HttpServletRequest):
            ResponseEntity<ConversationVO> {

        val user = userRepository.findByUsername(request.userPrincipal.name) as User
        val conversationThread = conversationService.retrieveThread(conversationId)

        return ResponseEntity.ok(conversationAssembler.toConversationVO(conversationThread, user.id))
    }
}