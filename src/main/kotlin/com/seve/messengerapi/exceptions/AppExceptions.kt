package com.seve.messengerapi.exceptions

import java.lang.RuntimeException

class UsernameUnavailableException(override val message: String) : RuntimeException()

class InvalidUserIdException(override val message: String) : RuntimeException()

class MessageEmptyException(override val message: String = "A message cannot be empty.") : RuntimeException()

class MessageRecipientInvalidException(override val message: String) : RuntimeException()

class ConversationInvalidException(override val message: String) : RuntimeException()

class UserDeactivatedException(override val message: String) : RuntimeException()

class UserStatusEmptyException(override val message: String = "A user's status cannot be empty.") : RuntimeException()

class BusinessExeption(override val message: String = "Conflict") : RuntimeException()