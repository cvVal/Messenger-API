package com.seve.messengerapi.service

import com.seve.messengerapi.domain.UserRequest
import com.seve.messengerapi.domain.UserResponse
import com.seve.messengerapi.exceptions.BusinessExeption
import com.seve.messengerapi.exceptions.InvalidUserIdException
import com.seve.messengerapi.exceptions.UserStatusEmptyException
import com.seve.messengerapi.exceptions.UsernameUnavailableException
import com.seve.messengerapi.models.User
import com.seve.messengerapi.repositories.UserRepository
import ma.glasnost.orika.MapperFacade
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class UserServiceImpl(
        val repository: UserRepository,
        val mapperFacade: MapperFacade) : UserService {

    @Transactional
    @Throws(UsernameUnavailableException::class)
    override fun attemptRegistration(userDetails: UserRequest): UserResponse {

        if (!userNameExist(userDetails.username!!)){

            val user = User()
            user.username = userDetails.username!!
            user.phoneNumber = userDetails.phoneNumber!!
            user.password = userDetails.password!!

            repository.save(user)
            obscurePassword(user)

            return mapperFacade.map(user, UserResponse::class.java)
        }

        throw UsernameUnavailableException("The username ${userDetails.username} is unavailable")
    }

    override fun listUsers(currentUser: User): List<User> {
        return repository.findAll().mapTo(ArrayList()) { it }.filter { it != currentUser }
    }

    @Throws(InvalidUserIdException::class, BusinessExeption::class)
    override fun retrieveUserData(request: UserRequest?): UserResponse? {

        validateRequest(request!!)

        if (request.username != null) {
            val user = repository.findByUsername(request.username!!)

            if (user != null) {
                obscurePassword(user)

                return mapperFacade.map(user, UserResponse::class.java)
            }
        }

        if (request.userId != null) {
            val user = repository.findById(request.userId!!)

            if (user.isPresent) {
                val user = user.get()
                obscurePassword(user)

                return mapperFacade.map(user, UserResponse::class.java)
            }
        }

        if (request.username != null) throw InvalidUserIdException("The username ${request.username} does not exist.")
            else throw InvalidUserIdException("The user id ${request.userId} does not exist.")
    }

    override fun userNameExist(username: String): Boolean {
        return repository.findByUsername(username) != null
    }

    @Throws(UserStatusEmptyException::class)
    fun updateUserStatus(currentUser: User, updateDetails: User): User {

        if (updateDetails.status.isNotEmpty()) {
            currentUser.status = updateDetails.status

            repository.save(currentUser)

            return currentUser
        }
        throw UserStatusEmptyException()
    }

    private fun validateRequest(request: UserRequest) {

        if (request.userId == null && request.username == null) {
            throw BusinessExeption("Must fill at least one field")
        }

        if (request.userId != null && request.username != null) {
            throw BusinessExeption("The search cannot combine userId and username")
        }
    }

    private fun obscurePassword(user: User?) {
        user!!.password = "XXX XXXX XXX"
    }
}