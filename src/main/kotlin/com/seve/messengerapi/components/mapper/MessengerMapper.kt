package com.seve.messengerapi.components.mapper

import com.seve.messengerapi.domain.UserResponse
import com.seve.messengerapi.models.Conversation
import com.seve.messengerapi.models.User
import ma.glasnost.orika.MapperFactory
import ma.glasnost.orika.impl.ConfigurableMapper
import org.springframework.stereotype.Component

@Component
class MessengerMapper : ConfigurableMapper() {

    override fun configure(factory: MapperFactory) {
        configureMapper(factory)
    }

    private fun configureMapper(factory: MapperFactory): MapperFactory {

        classMapUserToUserResponse(factory)
        return factory
    }

    private fun classMapUserToUserResponse(factory: MapperFactory) {

        factory.classMap(User::class.java, UserResponse::class.java)
                .field("id", "id")
                .field("username", "username")
                .field("phoneNumber", "phoneNumber")
                .field("password", "password")
                .field("status", "status")
                .field("createdAt", "createdAt")
                .byDefault()
                .register()
    }
}