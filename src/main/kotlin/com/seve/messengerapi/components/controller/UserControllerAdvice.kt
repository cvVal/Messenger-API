package com.seve.messengerapi.components.controller

import com.seve.messengerapi.constants.ErrorResponse
import com.seve.messengerapi.constants.ResponseConstants
import com.seve.messengerapi.exceptions.BusinessExeption
import com.seve.messengerapi.exceptions.InvalidUserIdException
import com.seve.messengerapi.exceptions.UserStatusEmptyException
import com.seve.messengerapi.exceptions.UsernameUnavailableException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class UserControllerAdvice {

    @ExceptionHandler(UsernameUnavailableException::class)
    fun usernameUnavailable(usernameUnavailableException: UsernameUnavailableException):
            ResponseEntity<ErrorResponse> {

        val response = ErrorResponse(ResponseConstants.USERNAME_UNAVAILABLE.value, usernameUnavailableException.message)

        return ResponseEntity.unprocessableEntity().body(response)
    }

    @ExceptionHandler(InvalidUserIdException::class)
    fun invalidId(invalidUserIdException: InvalidUserIdException):
            ResponseEntity<ErrorResponse> {

        val response = ErrorResponse(ResponseConstants.INVALID_USER_ID.value, invalidUserIdException.message)

        return ResponseEntity.unprocessableEntity().body(response)
    }

    @ExceptionHandler(UserStatusEmptyException::class)
    fun statusEmpty(userStatusEmptyException: UserStatusEmptyException):
            ResponseEntity<ErrorResponse> {

        val response = ErrorResponse(ResponseConstants.EMPTY_STATUS.value, userStatusEmptyException.message)

        return ResponseEntity.unprocessableEntity().body(response)
    }

    @ExceptionHandler(BusinessExeption::class)
    fun businessExeption(businessException: BusinessExeption):
            ResponseEntity<ErrorResponse> {

        val response = ErrorResponse(ResponseConstants.BUSINESS.value, businessException.message)

        return ResponseEntity.unprocessableEntity().body(response)
    }
}