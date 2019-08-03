package com.seve.messengerapi.components

import com.seve.messengerapi.exceptions.UserDeactivatedException
import com.seve.messengerapi.models.User
import com.seve.messengerapi.repositories.UserRepository
import org.springframework.stereotype.Component
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import java.security.Principal
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AccountValidityInterceptor(
        val userRepo: UserRepository) : HandlerInterceptorAdapter() {

    @Throws(UserDeactivatedException::class)
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {

        val principal: Principal? = request.userPrincipal

        if (principal != null) {

            val user = userRepo.findByUsername(principal.name) as User

            if (user.accountStatus == "deactivated")
                throw UserDeactivatedException("The account of this user has been deactivated")
        }

        return super.preHandle(request, response, handler)
    }
}