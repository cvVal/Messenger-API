package com.seve.messengerapi.service

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

internal object TokenAuthenticationService {

    private const val TOKEN_EXPIRY: Long = 864_000_000
    private const val SECRET = "$78gr43g7g8feb8we"
    private const val TOKEN_PREFIX = "Bearer"
    private const val AUTHORIZATION_HEADER_KEY = "Authorization"

    fun addAuthentication(response: HttpServletResponse, username: String) {

        val jsonWebToken = Jwts.builder()
                .setSubject(username)
                .setExpiration(Date(System.currentTimeMillis() + TOKEN_EXPIRY))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact()

        response.addHeader(AUTHORIZATION_HEADER_KEY, "$TOKEN_PREFIX $jsonWebToken")
    }

    fun getAuthentication(request: HttpServletRequest): Authentication? {

        val token = request.getHeader(AUTHORIZATION_HEADER_KEY)

        if (token != null) {

            val user = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .body
                    .subject

            if (user != null) {

                return UsernamePasswordAuthenticationToken(user, null, emptyList<GrantedAuthority>())
            }
        }

        return null
    }
}