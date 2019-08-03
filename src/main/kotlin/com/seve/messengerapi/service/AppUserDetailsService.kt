package com.seve.messengerapi.service

import com.seve.messengerapi.repositories.UserRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component

@Component
class AppUserDetailsService(
        val userRepository: UserRepository) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {

        val user = userRepository.findByUsername(username) ?:
                throw UsernameNotFoundException("A user with the $username does not exist.")

        return User(user.username, user.password, ArrayList<GrantedAuthority>())
    }
}