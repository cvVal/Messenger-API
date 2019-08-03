package com.seve.messengerapi.filters

import com.fasterxml.jackson.databind.ObjectMapper
import com.seve.messengerapi.security.AccountCredentials
import com.seve.messengerapi.service.TokenAuthenticationService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTLoginFilter(url: String, authManager: AuthenticationManager) :
        AbstractAuthenticationProcessingFilter(AntPathRequestMatcher(url)) {

    init {
        authenticationManager = authManager
    }

    @Throws(AuthenticationException::class, IOException::class, ServletException::class)
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {

        val credentials = ObjectMapper()
                .readValue(request.inputStream, AccountCredentials::class.java)

        return authenticationManager.authenticate(UsernamePasswordAuthenticationToken(
                credentials.username,
                credentials.password,
                emptyList()))
    }

    override fun successfulAuthentication(request: HttpServletRequest, response: HttpServletResponse,
                                          chain: FilterChain, authResult: Authentication) {

        TokenAuthenticationService.addAuthentication(response, authResult.name)
    }
}