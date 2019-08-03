package com.seve.messengerapi.components.controller

import com.seve.messengerapi.constants.ErrorResponse
import com.seve.messengerapi.constants.ResponseConstants
import com.seve.messengerapi.exceptions.UserDeactivatedException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class RestController {

    @ExceptionHandler(UserDeactivatedException::class)
    fun userDeactivated(userDeactivatedException: UserDeactivatedException):
            ResponseEntity<ErrorResponse> {

        val response = ErrorResponse(ResponseConstants.ACCOUNT_DEACTIVATED.value, userDeactivatedException.message)

        return ResponseEntity(response, HttpStatus.UNAUTHORIZED)
    }
}