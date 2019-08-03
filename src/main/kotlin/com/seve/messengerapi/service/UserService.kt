package com.seve.messengerapi.service

import com.seve.messengerapi.domain.UserRequest
import com.seve.messengerapi.domain.UserResponse
import com.seve.messengerapi.models.User

interface UserService {

    fun attemptRegistration(userDetails: UserRequest): UserResponse
    fun listUsers(currentUser: User): List<User>
    fun retrieveUserData(request: UserRequest?): UserResponse?
    fun userNameExist(username: String): Boolean
}