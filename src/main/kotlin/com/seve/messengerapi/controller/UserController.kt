package com.seve.messengerapi.controller

import com.seve.messengerapi.domain.UserRequest
import com.seve.messengerapi.domain.UserResponse
import com.seve.messengerapi.models.User
import com.seve.messengerapi.repositories.UserRepository
import com.seve.messengerapi.service.UserServiceImpl
import ma.glasnost.orika.MapperFacade
import org.springframework.format.annotation.NumberFormat
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid
import javax.validation.constraints.Digits
import javax.ws.rs.QueryParam

@RestController
@RequestMapping("/users", produces = ["application/json"])
@Validated
class UserController(
        val userService: UserServiceImpl,
        val userRepository: UserRepository,
        val mapperFacade: MapperFacade) {

    @RequestMapping(value = ["/registrations"], method = [(RequestMethod.POST)])
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser(
            @Valid
            @RequestBody userDetails: UserRequest): UserResponse {

        return userService.attemptRegistration(userDetails)
    }

    @RequestMapping(value = ["/userData"], method = [(RequestMethod.GET)])
    @ResponseStatus(HttpStatus.OK)
    fun getUserData(
            @Valid
            @Digits(integer = 100, fraction = 0, message = "Must be a number")
            @NumberFormat(style = NumberFormat.Style.NUMBER)
            @QueryParam(value = "userId")
            userId: Long?,

            @QueryParam(value = "username")
            username: String?): UserResponse? {

        val request = UserRequest()
        request.userId = userId
        request.username = username

        return userService.retrieveUserData(request)
    }

    @RequestMapping(value = ["/details"], method = [(RequestMethod.GET)])
    @ResponseStatus(HttpStatus.OK)
    fun getUserDetails(request: HttpServletRequest): UserResponse {

        val user = userRepository.findByUsername(request.userPrincipal.name) as User

        return mapperFacade.map(user, UserResponse::class.java)
    }

    @GetMapping
    fun index(request: HttpServletRequest): UserResponse {

        val user = userRepository.findByUsername(request.userPrincipal.name) as User

        val users = userService.listUsers(user)

        return mapperFacade.map(users, UserResponse::class.java)
    }

    @PutMapping
    fun updateUserStatus(@RequestBody updateDetails: User, request: HttpServletRequest): UserResponse {

        val currentUser = userRepository.findByUsername(request.userPrincipal.name)

        userService.updateUserStatus(currentUser as User, updateDetails)

        return mapperFacade.map(currentUser, UserResponse::class.java)
    }
}