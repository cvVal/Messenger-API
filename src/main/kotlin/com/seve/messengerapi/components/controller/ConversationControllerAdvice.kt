package com.seve.messengerapi.components.controller

import com.seve.messengerapi.constants.ErrorResponse
import com.seve.messengerapi.exceptions.ConversationInvalidException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ConversationControllerAdvice {

    @ExceptionHandler(ConversationInvalidException::class)
    fun conversationIdInvalid(conversationInvalidException: ConversationInvalidException):
            ResponseEntity<ErrorResponse> {

        val response = ErrorResponse("", conversationInvalidException.message)

        return ResponseEntity.unprocessableEntity().body(response)
    }
}